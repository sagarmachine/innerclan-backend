package com.innerclan.v1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductDto extends  AddProductDto{

    public UpdateProductDto(@NotNull(message = "name should not be null") String productName, @NotNull double productPrice, @NotNull double actualPrice, String comment, @NotNull long id) {
        super(productName, productPrice, actualPrice, comment);
        this.id = id;
    }

    @NotNull
    long id;
}
