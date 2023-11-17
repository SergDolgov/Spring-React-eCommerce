package com.letsanjoy.xsonic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letsanjoy.xsonic.dto.review.ReviewRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.letsanjoy.xsonic.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;
import static com.letsanjoy.xsonic.constants.ErrorMessage.PRODUCT_NOT_FOUND;
import static com.letsanjoy.xsonic.constants.PathConstants.API_V1_REVIEW;
import static com.letsanjoy.xsonic.constants.PathConstants.PRODUCT_ID;
import static com.letsanjoy.xsonic.util.TestConstants.FIRST_NAME;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-products-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-products-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getReviewsByProductId() throws Exception {
        mockMvc.perform(get(API_V1_REVIEW + PRODUCT_ID, 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].author").isNotEmpty())
                .andExpect(jsonPath("$[*].message").isNotEmpty())
                .andExpect(jsonPath("$[*].rating").isNotEmpty())
                .andExpect(jsonPath("$[*].date").isNotEmpty());
    }

    @Test
    public void addReviewToProduct() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setProductId(1L);
        reviewRequest.setAuthor(FIRST_NAME);
        reviewRequest.setMessage("Hello world");
        reviewRequest.setRating(5);

        mockMvc.perform(post(API_V1_REVIEW)
                        .content(mapper.writeValueAsString(reviewRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.author", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.rating", equalTo(5)))
                .andExpect(jsonPath("$.message", equalTo("Hello world")));
    }

    @Test
    public void addReviewToProduct_ShouldNotFound() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setProductId(111L);
        reviewRequest.setAuthor(FIRST_NAME);
        reviewRequest.setMessage("Hello world");
        reviewRequest.setRating(5);

        mockMvc.perform(post(API_V1_REVIEW)
                        .content(mapper.writeValueAsString(reviewRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo(PRODUCT_NOT_FOUND)));
    }

    @Test
    public void addReviewToProduct_ShouldInputFieldsAreEmpty() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest();

        mockMvc.perform(post(API_V1_REVIEW)
                        .content(mapper.writeValueAsString(reviewRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.authorError", is(FILL_IN_THE_INPUT_FIELD)))
                .andExpect(jsonPath("$.messageError", is(FILL_IN_THE_INPUT_FIELD)));
    }
}
