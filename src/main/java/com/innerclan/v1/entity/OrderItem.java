package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    double price;

//    @Lob
//    @Column(nullable = false)
//    byte[] image;

    String image;
    String deleteImage;

    @Column(nullable = false)
    long quantity;

    @Column(nullable = false)
    String size;

    @Column(nullable = false)
    String color;

}
