package com.letsanjoy.xsonic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letsanjoy.xsonic.dto.product.ProductSearchRequest;
import com.letsanjoy.xsonic.dto.product.SearchTypeRequest;
import com.letsanjoy.xsonic.enums.SearchProduct;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.letsanjoy.xsonic.constants.ErrorMessage.PRODUCT_NOT_FOUND;
import static com.letsanjoy.xsonic.constants.PathConstants.*;
import static com.letsanjoy.xsonic.util.TestConstants.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private ProductSearchRequest filter;

    @BeforeEach
    public void init() {
        List<Integer> prices = new ArrayList<>();
        List<String> brands = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        brands.add(BRAND_SONY);
        categories.add(CATEGORY);
        prices.add(1);
        prices.add(10000);

        filter = new ProductSearchRequest();
        filter.setBrands(brands);
        filter.setCategories(categories);
        filter.setPrices(prices);
        filter.setSortByPrice(true);

    }

    @Test
    public void getAllProducts() throws Exception {
        mockMvc.perform(get(API_V1_PRODUCTS)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void getProductById() throws Exception {
        mockMvc.perform(get(API_V1_PRODUCTS + PRODUCT_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("JBL Live 660NC")))
                .andExpect(jsonPath("$.brand", equalTo("JBL")))
                .andExpect(jsonPath("$.category", equalTo("Headphones")));
    }

    @Test
    public void getProductById_ShouldNotFound() throws Exception {
        mockMvc.perform(get(API_V1_PRODUCTS + PRODUCT_ID, 1111)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo(PRODUCT_NOT_FOUND)));
    }

    @Test
    public void getProductsByIds() throws Exception {
        mockMvc.perform(post(API_V1_PRODUCTS + IDS)
                        .content(mapper.writeValueAsString(Arrays.asList(3L, 4L, 5L)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void findProductsByFilterParams() throws Exception {
        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH)
                        .content(mapper.writeValueAsString(filter))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void findProductsByFilterParamsBrands() throws Exception {
        ProductSearchRequest filter = new ProductSearchRequest();
        List<String> brands = new ArrayList<>();
        brands.add(BRAND_SONY);
        List<Integer> prices = new ArrayList<>();
        prices.add(150);
        prices.add(25000);

        filter.setBrands(brands);
        //filter.setCategories(new ArrayList<>());
        filter.setPrices(prices);
        filter.setSortByPrice(true);

        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH)
                        .content(mapper.writeValueAsString(filter))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void findByCategory() throws Exception {
        ProductSearchRequest filter = new ProductSearchRequest();
        filter.setCategory(CATEGORY);

        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH_CATEGORY)
                        .content(mapper.writeValueAsString(filter))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void findByBrand() throws Exception {
        ProductSearchRequest filter = new ProductSearchRequest();
        filter.setBrand(BRAND_SONY);

        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH_BRAND)
                        .content(mapper.writeValueAsString(filter))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void findByInputText() throws Exception {
        SearchTypeRequest request = new SearchTypeRequest();
        request.setSearchType(SearchProduct.CATEGORY);
        request.setText(CATEGORY);
        
        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH_TEXT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(7)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());

        request.setSearchType(SearchProduct.BRAND);
        request.setText(BRAND_SONY);
        
        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH_TEXT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());

        request.setSearchType(SearchProduct.TITLE);
        request.setText("boAt A");

        mockMvc.perform(post(API_V1_PRODUCTS + SEARCH_TEXT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].filename").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

}
