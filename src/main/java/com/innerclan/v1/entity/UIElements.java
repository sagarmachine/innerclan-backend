package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UIElements {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    @Lob
    @Column(nullable = false)
    String quote;

    @Lob
    @Column(nullable = false)
    String image1;

    @Lob
    String image2;


}
