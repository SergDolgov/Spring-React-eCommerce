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

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "poducer")
    private String poducer;

    @Column(name = "year")
    private Integer year;
    
    @Column(name = "country")
    private String country;

    @Column(name = "product_gender")
    private String productGender;

    @Column(name = "fragrance_top_notes")
    private String fragranceTopNotes;

    @Column(name = "fragrance_middle_notes")
    private String fragranceMiddleNotes;
    
    @Column(name = "fragrance_base_notes")
    private String fragranceBaseNotes;

    @Column(name = "description")
    private String description;
    
    @Column(name = "filename")
    private String filename;
    
    @Column(name = "price")
    private Integer price;
    
    @Column(name = "volume")
    private String volume;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "product_rating")
    private Double productRating;

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
