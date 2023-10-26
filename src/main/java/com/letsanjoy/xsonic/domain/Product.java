package com.letsanjoy.xsonic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", initialValue = 109, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "brand")
    private String brand;

    @Column(name = "rate_count")
    private Integer rateCount;
    
    @Column(name = "category")
    private String category;

    @Column(name = "connectivity")
    private String connectivity;

    @Column(name = "final_price")
    private Integer finalPrice;

    @Column(name = "original_price")
    private Integer originalPrice;
    
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;
    
    @Column(name = "filename")
    private String filename;
    
    @Column(name = "price")
    private Integer price;
    
    @Column(name = "info")
    private String info;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "ratings")
    private Double ratings;

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
