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

    @NotNull(message = "name should not be empty")
    String productName;

    @NotNull(message = "product's price should not be empty")
  double productPrice;

    @NotNull(message = "product's actual price should not be empty")
   double actualPrice;

    String comment;


}
