package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String uuid;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    String phone;

    @Embedded
    Address address;

    @CreationTimestamp
    Date createdOn;

    Date lastLoggedIn;

    int visit;

    boolean newUser;

    int totalOrder;

    @OneToMany(mappedBy = "client")
    Set<Promo> promos;

    public void addPromos(Promo promo){
        if(promos==null){
            promos= new HashSet<Promo>();
        }
        promos.add(promo);
        promo.setClient(this);
    }




}
