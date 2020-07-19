package com.innerclan.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductView {

    long id;

    String productName;

    double productPrice;

    double actualPrice;

    byte[] defaultImage;

    long size;

}
