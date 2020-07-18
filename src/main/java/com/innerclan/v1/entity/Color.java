package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name="product_color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String colorName;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name="Product_id")
    Product product;

    @OneToMany(mappedBy = "color",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    List<Image> images;

    public void addImages(Image image){
        if(images==null){
            images= new ArrayList<Image>();
        }
        images.add(image);
        image.setColor(this);
    }





}
