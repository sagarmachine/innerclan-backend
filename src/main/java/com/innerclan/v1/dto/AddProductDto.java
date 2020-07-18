package com.innerclan.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {

    @NotNull(message = "name should not be null")
    String name;

    @NotNull
    double price;

    @NotNull
    double actualPrice;

    @NotNull
    String comment;


}
