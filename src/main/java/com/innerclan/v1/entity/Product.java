package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Entity
@Getter @Setter
 @NoArgsConstructor
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



    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    Category category;


    @CreationTimestamp
    Date createdOn;

    Date updatedOn;

    String comment;

    int view;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    Set<Color> colors;

    public void addColors(Color color){
        if(colors==null){
            colors= new HashSet<Color>();
        }
        colors.add(color);
        color.setProduct(this);
    }


}
