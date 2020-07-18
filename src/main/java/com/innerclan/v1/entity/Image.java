package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name="product_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @Lob
    @Column(nullable = false)
    byte[] image;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name="color_id")
    Color color;




}

