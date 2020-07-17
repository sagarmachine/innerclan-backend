package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Lob;
import java.util.List;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Color {

    @Column(nullable = false)
    String colorName;

    @Lob
    @Embedded
    List<Image> images;



}
