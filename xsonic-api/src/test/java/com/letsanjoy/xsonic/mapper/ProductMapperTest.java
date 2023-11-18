package com.letsanjoy.xsonic.mapper;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.dto.product.ProductRequest;
import com.letsanjoy.xsonic.dto.product.FullProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.letsanjoy.xsonic.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void convertToEntity() {
        ProductRequest productRequest = new ProductRequest();
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

        Product product = modelMapper.map(productRequest, Product.class);
        assertEquals(productRequest.getBrand(), product.getBrand());
        assertEquals(productRequest.getTitle(), product.getTitle());
        assertEquals(productRequest.getRateCount(), product.getRateCount());
        assertEquals(productRequest.getCategory(), product.getCategory());
        assertEquals(productRequest.getConnectivity(), product.getConnectivity());
        assertEquals(productRequest.getFinalPrice(), product.getFinalPrice());
        assertEquals(productRequest.getOriginalPrice(), product.getOriginalPrice());
        assertEquals(productRequest.getQuantity(), product.getQuantity());
        assertEquals(productRequest.getPrice(), product.getPrice());
        assertEquals(productRequest.getInfo(), product.getInfo());
        assertEquals(productRequest.getType(), product.getType());
    }

    @Test
    public void convertToResponseDto() {
        Product product = new Product();
        product.setId(1L);
        product.setBrand(BRAND_SONY);
        product.setTitle(TITLE);
        product.setRateCount(RATE_COUNT);
        product.setCategory(CATEGORY);
        product.setConnectivity(CONNECTIVITY);
        product.setFinalPrice(FINAL_PRICE);
        product.setOriginalPrice(ORIGINAL_PRICE);
        product.setQuantity(QUANTITY);
        product.setPrice(PRICE);
        product.setInfo(INFO);
        product.setType(TYPE);

        FullProductResponse fullProductResponse = modelMapper.map(product, FullProductResponse.class);
        assertEquals(product.getId(), fullProductResponse.getId());
        assertEquals(product.getBrand(), fullProductResponse.getBrand());
        assertEquals(product.getTitle(), fullProductResponse.getTitle());
        assertEquals(product.getRateCount(), fullProductResponse.getRateCount());
        assertEquals(product.getCategory(), fullProductResponse.getCategory());
        assertEquals(product.getConnectivity(), fullProductResponse.getConnectivity());
        assertEquals(product.getFinalPrice(), fullProductResponse.getFinalPrice());
        assertEquals(product.getOriginalPrice(), fullProductResponse.getOriginalPrice());
        assertEquals(product.getQuantity(), fullProductResponse.getQuantity());
        assertEquals(product.getPrice(), fullProductResponse.getPrice());
        assertEquals(product.getInfo(), fullProductResponse.getInfo());
        assertEquals(product.getType(), fullProductResponse.getType());
    }
}
