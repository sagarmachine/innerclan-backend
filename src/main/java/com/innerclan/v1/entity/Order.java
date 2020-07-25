package com.innerclan.v1.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    @Column(nullable = false, unique = true)
    String orderId;

    @Column(nullable = false, unique = true)
    String transactionId;

    @ManyToOne
    @JoinColumn
    Client client;

    @Column(nullable = false)
    Date date;

    Date expectedDeliveryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(nullable = false)
    double total;

    @Column(nullable = false)
    double promoDiscount;


    @Embedded
    @Column(nullable = false)
    Address address;

    @Column(nullable = false)
    String paymentMethod;


    @ElementCollection
     @CollectionTable(name = "order_item", joinColumns = @JoinColumn(name = "order_id"))
    List<OrderItem> orderItems;


    @OneToOne
    OrderQuery orderQuery;


    @Override
    public int hashCode() {
        return Objects.hash(getOrderId());
    }


}
