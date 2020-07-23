package com.innerclan.v1.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;
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

    @Column(unique = true)
    String uuid;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false,unique = true)
    String email;

   // @Column(nullable = false)
    String password;

    @Column(unique=true,length=10)
    String phone;

    @Embedded
    Address address;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date lastLoggedIn;


    long visit;


    long totalOrder;

    @ManyToMany (cascade ={CascadeType.PERSIST})
    @JoinTable
    Set<Promo> promos;


   @OneToMany(mappedBy = "client",cascade = {CascadeType.ALL})
   Set<CartItem> cartItems;

    public void addCartItem(CartItem cartItem) {
        if (cartItems == null) {
            cartItems = new HashSet<CartItem>();
        }
        if(cartItems.contains(cartItem)){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }
        else {
            cartItem.setQuantity(1);
            cartItems.add(cartItem);
            cartItem.setClient(this);
        }

    }

    public void addPromos(Promo promo) {
        if (promos == null) {
            promos = new HashSet<Promo>();
        }
        promos.add(promo);
        promo.getClients().add(this);
    }
}