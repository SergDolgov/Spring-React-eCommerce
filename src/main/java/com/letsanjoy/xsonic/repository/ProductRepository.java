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

    List<Product> findByPoducerOrderByPriceDesc(String poducer);

    List<Product> findByProductGenderOrderByPriceDesc(String productGender);

    List<Product> findByIdIn(List<Long> productsIds);

    @Query("SELECT product FROM Product product WHERE product.id IN :productsIds")
    List<ProductProjection> getProductsByIds(List<Long> productsIds);

    @Query("SELECT product FROM Product product " +
            "WHERE (coalesce(:poducers, null) IS NULL OR product.poducer IN :poducers) " +
            "AND (coalesce(:genders, null) IS NULL OR product.productGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR product.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY CASE WHEN :sortByPrice = true THEN product.price ELSE -product.price END ASC")
    Page<ProductProjection> findProductsByFilterParams(
            List<String> poducers, 
            List<String> genders, 
            Integer priceStart, 
            Integer priceEnd, 
            boolean sortByPrice,
            Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE UPPER(product.poducer) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByPoducer(String text, Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE UPPER(product.productTitle) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByProductTitle(String text, Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE UPPER(product.country) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByManufacturerCountry(String text, Pageable pageable);
}
