package com.innerclan.v1.dto;

import com.innerclan.v1.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutDto {

    List<CartItem> cartItemList;

    String promo;

}
