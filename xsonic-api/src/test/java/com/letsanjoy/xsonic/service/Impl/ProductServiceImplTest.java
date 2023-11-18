package com.letsanjoy.xsonic.service.Impl;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.dto.product.ProductSearchRequest;
import com.letsanjoy.xsonic.repository.ProductRepository;
import com.letsanjoy.xsonic.repository.projection.ProductProjection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.letsanjoy.xsonic.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private SpelAwareProxyProjectionFactory factory;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findProductById() {
        Product product = new Product();
        product.setId(123L);

        when(productRepository.findById(123L)).thenReturn(java.util.Optional.of(product));
        productService.getProductById(123L);
        assertEquals(123L, product.getId());
        assertNotEquals(1L, product.getId());
        verify(productRepository, times(1)).findById(123L);
    }

    @Test
    public void findAllProducts() {
        Pageable pageable = PageRequest.of(0, 20);
        List<ProductProjection> productProjectionList = new ArrayList<>();
        productProjectionList.add(factory.createProjection(ProductProjection.class));
        productProjectionList.add(factory.createProjection(ProductProjection.class));
        Page<ProductProjection> productList = new PageImpl<>(productProjectionList);

        when(productRepository.findAllByOrderByIdAsc(pageable)).thenReturn(productList);
        productService.getAllProducts(pageable);
        assertEquals(2, productProjectionList.size());
        verify(productRepository, times(1)).findAllByOrderByIdAsc(pageable);
    }

    @Test
    public void filter() {
        Pageable pageable = PageRequest.of(0, 20);
        
        ProductProjection productSony = factory.createProjection(ProductProjection.class);         
        productSony.setBrand(BRAND_SONY);
        productSony.setCategory(CATEGORY);
        productSony.setPrice(101);
        ProductProjection productJbl = factory.createProjection(ProductProjection.class);
        productJbl.setBrand(BRAND_JBL);
        productJbl.setCategory(CATEGORY);
        productJbl.setPrice(102);
        Page<ProductProjection> productList = new PageImpl<>(Arrays.asList(productSony, productJbl));

        List<String> brands = new ArrayList<>();
        brands.add(BRAND_SONY);
        brands.add(BRAND_JBL);

        List<String> categories = new ArrayList<>();
        categories.add(CATEGORY);

        when(productRepository.findProductsByFilterParams(brands, categories, 1, 1000, false, pageable)).thenReturn(productList);
        ProductSearchRequest filter = new ProductSearchRequest();
        filter.setBrands(brands);
        filter.setCategories(categories);
        filter.setPrices(Arrays.asList(1, 20000));
        filter.setSortByPrice(false);
        productService.findProductsByFilterParams(filter, pageable);
        assertEquals(2, productList.getTotalElements());
        assertEquals(productList.getContent().get(0).getBrand(), BRAND_SONY);
        verify(productRepository, times(1)).findProductsByFilterParams(brands, categories, 1, 20000, false, pageable);
    }

    @Test
    public void findByBrandOrderByPriceDesc() {
        Product productSony = new Product();
        productSony.setBrand(BRAND_SONY);
        Product productJbl = new Product();
        productJbl.setBrand(BRAND_JBL);
        List<Product> productList = new ArrayList<>();
        productList.add(productSony);
        productList.add(productJbl);

        when(productRepository.findByBrandOrderByPriceDesc(BRAND_SONY)).thenReturn(productList);
        productService.findByBrand(BRAND_SONY);
        assertEquals(productList.get(0).getBrand(), BRAND_SONY);
        assertNotEquals(productList.get(0).getBrand(), BRAND_JBL);
        verify(productRepository, times(1)).findByBrandOrderByPriceDesc(BRAND_SONY);
    }

    @Test
    public void findByCategoryOrderByPriceDesc() {
        Product productSony = new Product();
        productSony.setCategory(CATEGORY);
        List<Product> productList = new ArrayList<>();
        productList.add(productSony);

        when(productRepository.findByCategoryOrderByPriceDesc(CATEGORY)).thenReturn(productList);
        productService.findByCategory(CATEGORY);
        assertEquals(productList.get(0).getCategory(), CATEGORY);
        assertNotEquals(productList.get(0).getCategory(), "male");
        verify(productRepository, times(1)).findByCategoryOrderByPriceDesc(CATEGORY);
    }

    @Test
    public void saveProduct() {
        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, FILE_NAME, "multipart/form-data", FILE_PATH.getBytes());
        Product product = new Product();
        product.setId(1L);
        product.setBrand(BRAND_SONY);
        product.setFilename(multipartFile.getOriginalFilename());

        when(productRepository.save(product)).thenReturn(product);
        productService.saveProduct(product, multipartFile);
        verify(productRepository, times(1)).save(product);
    }
}
