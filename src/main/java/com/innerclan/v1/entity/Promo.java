package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String name;

    @CreationTimestamp
    Date createdOn;


    Date expiryDate;

    @Enumerated(EnumType.STRING)
    Access access;

    double value;

    @ManyToOne(cascade =CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    Client client;






}


