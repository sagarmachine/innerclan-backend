package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable=false)
    long productId;

    long quantity;


    @JsonIgnore
    @ManyToOne
    @JoinColumn
    @NotNull
    Client client;

    public CartItem(Client client, long productId) {
        this.client=client;
        this.productId=productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return getProductId() == cartItem.getProductId() &&
                Objects.equals(getClient(), cartItem.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getClient());
    }
}
