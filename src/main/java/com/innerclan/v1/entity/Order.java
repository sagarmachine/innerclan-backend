package com.innerclan.v1.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

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

    String transactionId;

    @ManyToOne
    @JoinColumn
    Client client;

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(value = TemporalType.DATE)
    Date orderedOnDate;

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(value = TemporalType.TIME)
    Date orderedOnTime;


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


    String paymentMode;


    @ElementCollection
     @CollectionTable(name = "order_item", joinColumns = @JoinColumn(name = "order_id"))
    List<OrderItem> orderItems;


    @ElementCollection
    @CollectionTable(name = "order_query",joinColumns = @JoinColumn(name = "order_id"))
    List<String> queries;


    @Override
    public int hashCode() {
        return Objects.hash(getOrderId());
    }


}
