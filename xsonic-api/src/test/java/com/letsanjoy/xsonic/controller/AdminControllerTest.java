package com.letsanjoy.xsonic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letsanjoy.xsonic.dto.product.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static com.letsanjoy.xsonic.constants.ErrorMessage.*;
import static com.letsanjoy.xsonic.constants.PathConstants.*;
import static com.letsanjoy.xsonic.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WithUserDetails(ADMIN_EMAIL)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql", "/sql/create-products-before.sql", "/sql/create-orders-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-orders-after.sql", "/sql/create-products-after.sql", "/sql/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ProductRequest productRequest;

    @BeforeEach
    public void init() {
        productRequest = new ProductRequest();
        productRequest.setBrand(BRAND_SONY);
        productRequest.setTitle(TITLE);
        productRequest.setRateCount(RATE_COUNT);
        productRequest.setCategory(CATEGORY);
        productRequest.setConnectivity(CONNECTIVITY);
        productRequest.setFinalPrice(FINAL_PRICE);
        productRequest.setOriginalPrice(ORIGINAL_PRICE);
        productRequest.setQuantity(QUANTITY);
        productRequest.setPrice(PRICE);
        productRequest.setInfo(INFO);
        productRequest.setType(TYPE);
    }

    @Test
    @DisplayName("[200] POST /api/v1/admin/add - Add Product")
    public void addProduct() throws Exception {
        FileInputStream inputFile = new FileInputStream(new File(FILE_PATH));
        MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
        MockMultipartFile jsonFile = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsString(productRequest).getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(multipart(API_V1_ADMIN + ADD)
                        .file(multipartFile)
                        .file(jsonFile))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[400] POST /api/v1/admin/add - Should Input Fields Are Empty Add Product")
    public void addProduct_ShouldInputFieldsAreEmpty() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        MockMultipartFile jsonFile = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsString(productRequest).getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(multipart(API_V1_ADMIN + ADD)
                        .file(jsonFile)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.titleError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.brandError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.categoryError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.connectivityError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.finalPriceError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.originalPriceError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.quantityError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.infoError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.typeError", is(FILL_IN_THE_INPUT_FIELD)));
    }

    @Test
    @DisplayName("[200] POST /api/v1/admin/update - Edit Product")
    public void updateProduct() throws Exception {
        FileInputStream inputFile = new FileInputStream(new File(FILE_PATH));
        MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
        MockMultipartFile jsonFileEdit = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsString(productRequest).getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productRequest.setType("test");
        mockMvc.perform(multipart(API_V1_ADMIN + UPDATE)
                        .file(multipartFile)
                        .file(jsonFileEdit))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[400] POST /api/v1/admin/update - Should Input Fields Are Empty Edit Product")
    public void updateProduct_ShouldInputFieldsAreEmpty() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        MockMultipartFile jsonFile = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsString(productRequest).getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(multipart(API_V1_ADMIN + UPDATE)
                        .file(jsonFile)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.titleError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.brandError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.categoryError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.connectivityError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.finalPriceError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.originalPriceError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.quantityError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.infoError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.typeError", is(FILL_IN_THE_INPUT_FIELD)));
    }

    @Test
    @DisplayName("[200] DELETE /api/v1/admin/delete/16 - Delete Product")
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete(API_V1_ADMIN + DELETE_BY_PRODUCT_ID, 16)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Product deleted successfully")));
    }

    @Test
    @DisplayName("[404] DELETE /api/v1/admin/delete/99 - Delete Product Should Not Found")
    public void deleteProduct_ShouldNotFound() throws Exception {
        mockMvc.perform(delete(API_V1_ADMIN + DELETE_BY_PRODUCT_ID, 99)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(PRODUCT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/admin/orders - Get All Orders")
    public void getAllOrders() throws Exception {
        mockMvc.perform(get(API_V1_ADMIN + ORDERS)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].totalPrice", hasItem(TOTAL_PRICE)))
                .andExpect(jsonPath("$[*].date").isNotEmpty())
                .andExpect(jsonPath("$[*].firstName", hasItem(FIRST_NAME)))
                .andExpect(jsonPath("$[*].lastName", hasItem(LAST_NAME)))
                .andExpect(jsonPath("$[*].city", hasItem(CITY)))
                .andExpect(jsonPath("$[*].address", hasItem(ADDRESS)))
                .andExpect(jsonPath("$[*].email", hasItem(USER_EMAIL)))
                .andExpect(jsonPath("$[*].phoneNumber", hasItem(PHONE_NUMBER)))
                .andExpect(jsonPath("$[*].postIndex", hasItem(POST_INDEX)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/admin/order/test123@test.com - Get User Orders By Email")
    public void getUserOrdersByEmail() throws Exception {
        mockMvc.perform(get(API_V1_ADMIN + ORDER_BY_EMAIL, USER_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].totalPrice", hasItem(TOTAL_PRICE)))
                .andExpect(jsonPath("$[*].date").isNotEmpty())
                .andExpect(jsonPath("$[*].firstName", hasItem(FIRST_NAME)))
                .andExpect(jsonPath("$[*].lastName", hasItem(LAST_NAME)))
                .andExpect(jsonPath("$[*].city", hasItem(CITY)))
                .andExpect(jsonPath("$[*].address", hasItem(ADDRESS)))
                .andExpect(jsonPath("$[*].email", hasItem(USER_EMAIL)))
                .andExpect(jsonPath("$[*].phoneNumber", hasItem(PHONE_NUMBER)))
                .andExpect(jsonPath("$[*].postIndex", hasItem(POST_INDEX)));
    }

    @Test
    @DisplayName("[200] DELETE /api/v1/admin/order/delete/111 - Delete Order")
    public void deleteOrder() throws Exception {
        mockMvc.perform(delete(API_V1_ADMIN + ORDER_DELETE, 111)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Order deleted successfully")));
    }

    @Test
    @DisplayName("[404] DELETE /api/v1/admin/order/delete/222 - Delete Order Should Not Found")
    public void deleteOrder_ShouldNotFound() throws Exception {
        mockMvc.perform(delete(API_V1_ADMIN + ORDER_DELETE, 222)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(ORDER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/admin/user/122 - Get User by Id")
    public void getUserById() throws Exception {
        mockMvc.perform(get(API_V1_ADMIN + USER_BY_ID, USER_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL));
    }

    @Test
    @DisplayName("[404] GET /api/v1/admin/user/1222 - Should Not Found Get User by Id")
    public void getUserById_ShouldNotFound() throws Exception {
        mockMvc.perform(get(API_V1_ADMIN + USER_BY_ID, 1222)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(USER_NOT_FOUND));
    }

    @Test
    @DisplayName("[200] GET /api/v1/admin/user/all - Get All Users")
    public void getAllUsers() throws Exception {
        mockMvc.perform(get(API_V1_ADMIN + USER_ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(USER_ID)))
                .andExpect(jsonPath("$[*].firstName", hasItem(FIRST_NAME)))
                .andExpect(jsonPath("$[*].email", hasItem(USER_EMAIL)));
    }

}
