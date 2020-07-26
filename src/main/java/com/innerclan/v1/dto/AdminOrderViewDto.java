package com.innerclan.v1.dto;

import com.innerclan.v1.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderViewDto {


    long id;

    String orderId;

    String transactionId;

//    @ManyToOne
//    @JoinColumn
//    Client client;


    String email;


    Date date;

    Date expectedDeliveryDate;


    OrderStatus status;


    double total;


    double promoDiscount;


    Address address;


    String payMethod;

    List<OrderItem> orderItems;

 List<String> queries;
}
