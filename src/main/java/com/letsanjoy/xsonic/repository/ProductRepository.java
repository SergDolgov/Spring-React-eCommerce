package com.letsanjoy.xsonic.repository;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.repository.projection.ProductProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<ProductProjection> findAllByOrderByIdAsc();
    
    @Query("SELECT product FROM Product product ORDER BY product.id ASC")
    Page<ProductProjection> findAllByOrderByIdAsc(Pageable pageable);

    List<Product> findByBrandOrderByPriceDesc(String brand);

    List<Product> findByConnectivityOrderByPriceDesc(String connectivity);

    List<Product> findByIdIn(List<Long> productsIds);

    @Query("SELECT product FROM Product product WHERE product.id IN :productsIds")
    List<ProductProjection> getProductsByIds(List<Long> productsIds);

    @Query("SELECT product FROM Product product " +
            "WHERE (coalesce(:brands, null) IS NULL OR product.brand IN :brands) " +
            "AND (coalesce(:connectivities, null) IS NULL OR product.connectivity IN :connectivities) " +
            "AND (coalesce(:priceStart, null) IS NULL OR product.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY CASE WHEN :sortByPrice = true THEN product.price ELSE -product.price END ASC")
    Page<ProductProjection> findProductsByFilterParams(
            List<String> brands, 
            List<String> connectivities, 
            Integer priceStart, 
            Integer priceEnd, 
            boolean sortByPrice,
            Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE UPPER(product.brand) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByBrand(String text, Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE UPPER(product.title) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByTitle(String text, Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE UPPER(product.category) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByCategory(String text, Pageable pageable);
}
