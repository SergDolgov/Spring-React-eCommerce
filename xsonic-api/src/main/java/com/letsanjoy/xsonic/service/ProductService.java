package com.letsanjoy.xsonic.service;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.dto.product.ProductSearchRequest;
import com.letsanjoy.xsonic.enums.SearchProduct;
import com.letsanjoy.xsonic.repository.projection.FullProductProjection;
import com.letsanjoy.xsonic.repository.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId);

    Page<FullProductProjection> getAdminProducts(Pageable pageable);

    Page<ProductProjection> getAllProducts(Pageable pageable);

    List<ProductProjection> getProductsByIds(List<Long> productsId);

    Page<ProductProjection> findProductsByFilterParams(ProductSearchRequest filter, Pageable pageable);

    List<Product> findByBrand(String brand);

    List<Product> findByConnectivity(String connectivity);
    
    Page<ProductProjection> findByInputText(SearchProduct searchType, String text, Pageable pageable);

    Product saveProduct(Product product, MultipartFile file);

    String deleteProduct(Long productId);

}
