package com.innerclan.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {

    long selectedProductId;

    String productName;

    double productPrice;

    long selectedColorId;

    String selectedColorName;

    byte[] selectedColorImage;

    long quantity;

    String size;



}

