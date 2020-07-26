package com.innerclan.v1.dto;


import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.OrderItem;
import com.innerclan.v1.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    String email;

    Date orderedOnDate;


    Date orderedOnTime;

    Date updatedOn;


    Date expectedDeliveryDate;


    OrderStatus status;

    double total;

    double promoDiscount;


}
