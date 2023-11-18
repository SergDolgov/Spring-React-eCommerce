package com.letsanjoy.xsonic.controller;

import java.util.List;

import com.letsanjoy.xsonic.constants.PathConstants;
import com.letsanjoy.xsonic.dto.HeaderResponse;
import com.letsanjoy.xsonic.dto.product.*;
import com.letsanjoy.xsonic.mapper.ProductMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_PRODUCTS)
public class ProductController {

    private final ProductMapper productMapper;

    @GetMapping("/short")
    public ResponseEntity<List<ProductResponse>> getAllProducts(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<ProductResponse> response = productMapper.getAllProducts(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping()
    public ResponseEntity<List<FullProductResponse>> getProducts(@PageableDefault(size = 50) Pageable pageable) {
        HeaderResponse<FullProductResponse> response = productMapper.getAdminProducts(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.PRODUCT_ID)
    public ResponseEntity<FullProductResponse> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.getProductById(productId));
    }

    @PostMapping(PathConstants.IDS)
    public ResponseEntity<List<ProductResponse>> getProductsByIds(@RequestBody List<Long> productsIds) {
        return ResponseEntity.ok(productMapper.getProductsByIds(productsIds));
    }

    @PostMapping(PathConstants.SEARCH)
    public ResponseEntity<List<ProductResponse>> findProductsByFilterParams(@RequestBody ProductSearchRequest filter,
                                                                            @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<ProductResponse> response = productMapper.findProductsByFilterParams(filter, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(PathConstants.SEARCH_CATEGORY)
    public ResponseEntity<List<ProductResponse>> findByCategory(@RequestBody ProductSearchRequest filter) {
        return ResponseEntity.ok(productMapper.findByCategory(filter.getCategory()));
    }

    @PostMapping(PathConstants.SEARCH_BRAND)
    public ResponseEntity<List<ProductResponse>> findByBrand(@RequestBody ProductSearchRequest filter) {
        return ResponseEntity.ok(productMapper.findByBrand(filter.getBrand()));
    }

    @PostMapping(PathConstants.SEARCH_TEXT)
    public ResponseEntity<List<ProductResponse>> findByInputText(@RequestBody SearchTypeRequest searchType,
                                                                 @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<ProductResponse> response = productMapper.findByInputText(searchType.getSearchType(), searchType.getText(), pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

}
