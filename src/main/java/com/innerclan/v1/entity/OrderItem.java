package com.innerclan.v1.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class OrderItem {

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    double price;

    @Lob
    @Column(nullable = false)
    byte[] image;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    String size;

}
