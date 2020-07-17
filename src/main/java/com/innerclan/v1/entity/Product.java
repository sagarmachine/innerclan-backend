package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Slf4j
@Entity
@Getter @Setter
 @AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    double productPrice;

    @Column(nullable = false)
    double actualPrice;

    @ElementCollection
    @CollectionTable(name="product_color",joinColumns = @JoinColumn(name="product_id"))
    Set<Color> colors;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    Category category;


    @CreationTimestamp
    Date createdOn;

    Date updatedOn;

    String comment;

    int view;

    public Product() {
        log.info("SSSS");
    }
}
