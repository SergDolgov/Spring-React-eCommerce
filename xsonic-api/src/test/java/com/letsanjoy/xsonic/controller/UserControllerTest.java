package com.letsanjoy.xsonic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letsanjoy.xsonic.dto.user.UpdateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.letsanjoy.xsonic.constants.ErrorMessage.EMPTY_FIRST_NAME;
import static com.letsanjoy.xsonic.constants.ErrorMessage.EMPTY_LAST_NAME;
import static com.letsanjoy.xsonic.constants.PathConstants.API_V1_USERS;
import static com.letsanjoy.xsonic.constants.PathConstants.CART;
import static com.letsanjoy.xsonic.util.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql", "/sql/create-products-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql", "/sql/create-products-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserInfo() throws Exception {
        mockMvc.perform(get(API_V1_USERS)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.roles").value(ROLE_USER));
    }

    @Test
    public void getUserInfoByToken() throws Exception {
        mockMvc.perform(get(API_V1_USERS)
                        .header("Authorization", "Bearer "+JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.roles").value(ROLE_ADMIN));
    }

    @Test
    public void getUserInfoByTokenExpired() throws Exception {
        //Exception exception = assertThrows(AuthenticationException.class, () -> {
            mockMvc.perform(get(API_V1_USERS)
                            .header("Authorization", "Bearer jwt")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isUnauthorized());
        //});

        //String actualMessage = exception.getMessage();
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserInfo() throws Exception {
        UpdateUserRequest userRequest = new UpdateUserRequest();
        userRequest.setFirstName(USER2_NAME);
        userRequest.setLastName(USER2_NAME);

        mockMvc.perform(put(API_V1_USERS)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER2_NAME))
                .andExpect(jsonPath("$.lastName").value(USER2_NAME));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserInfo_ShouldInputFieldsAreEmpty() throws Exception {
        UpdateUserRequest userRequest = new UpdateUserRequest();

        mockMvc.perform(put(API_V1_USERS)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstNameError", is(EMPTY_FIRST_NAME)))
                .andExpect(jsonPath("$.lastNameError", is(EMPTY_LAST_NAME)));
    }

    @Test
    public void getCart() throws Exception {
        List<Long> productsIds = new ArrayList<>();
        productsIds.add(2L);
        productsIds.add(4L);

        mockMvc.perform(post(API_V1_USERS + CART)
                        .content(mapper.writeValueAsString(productsIds))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty())
                .andExpect(jsonPath("$[*].info").isNotEmpty())
                .andExpect(jsonPath("$[*].ratings").isNotEmpty())
                .andExpect(jsonPath("$[*].reviewsCount").isNotEmpty());
    }

}
