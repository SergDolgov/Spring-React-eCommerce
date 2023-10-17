package com.letsanjoy.xsonic.mapper;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.dto.HeaderResponse;
import com.letsanjoy.xsonic.dto.product.FullProductResponse;
import com.letsanjoy.xsonic.dto.product.ProductRequest;
import com.letsanjoy.xsonic.dto.product.ProductResponse;
import com.letsanjoy.xsonic.dto.product.ProductSearchRequest;
import com.letsanjoy.xsonic.enums.SearchProduct;
import com.letsanjoy.xsonic.exception.InputFieldException;
import com.letsanjoy.xsonic.repository.projection.ProductProjection;
import com.letsanjoy.xsonic.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CommonMapper commonMapper;
    private final ProductService productService;

    public FullProductResponse getProductById(Long productId) {
        return commonMapper.convertToResponse(productService.getProductById(productId), FullProductResponse.class);
    }

    public List<ProductResponse> getProductsByIds(List<Long> productsId) {
        return commonMapper.convertToResponseList(productService.getProductsByIds(productsId), ProductResponse.class);
    }

    public HeaderResponse<ProductResponse> getAllProducts(Pageable pageable) {
        Page<ProductProjection> products = productService.getAllProducts(pageable);
        return commonMapper.getHeaderResponse(products.getContent(), products.getTotalPages(), products.getTotalElements(), ProductResponse.class);
    }

    public HeaderResponse<ProductResponse> findProductsByFilterParams(ProductSearchRequest filter, Pageable pageable) {
        Page<ProductProjection> products = productService.findProductsByFilterParams(filter, pageable);
        return commonMapper.getHeaderResponse(products.getContent(), products.getTotalPages(), products.getTotalElements(), ProductResponse.class);
    }

    public List<ProductResponse> findByPoducer(String poducer) {
        return commonMapper.convertToResponseList(productService.findByPoducer(poducer), ProductResponse.class);
    }

    public List<ProductResponse> findByProductGender(String productGender) {
        return commonMapper.convertToResponseList(productService.findByProductGender(productGender), ProductResponse.class);
    }
    
    public HeaderResponse<ProductResponse> findByInputText(SearchProduct searchType, String text, Pageable pageable) {
        Page<ProductProjection> products = productService.findByInputText(searchType, text, pageable);
        return commonMapper.getHeaderResponse(products.getContent(), products.getTotalPages(), products.getTotalElements(), ProductResponse.class);
    }

    public FullProductResponse saveProduct(ProductRequest productRequest, MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Product product = commonMapper.convertToEntity(productRequest, Product.class);
        return commonMapper.convertToResponse(productService.saveProduct(product, file), FullProductResponse.class);
    }

    public String deleteProduct(Long productId) {
        return productService.deleteProduct(productId);
    }
}
