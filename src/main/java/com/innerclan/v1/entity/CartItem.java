package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @NotNull
    @ManyToOne
    @JoinColumn
    Color color;

    @Column(nullable = false)
    String size;

    @Column(nullable = true)
    long quantity;


    @JsonIgnore
    @ManyToOne
    @JoinColumn
    @NotNull
    Client client;


    public CartItem(Client client, Color color,String Size) {
        this.client=client;
        this.color=color;
        this.size=size;
    }

    public CartItem(Client client,Color color, String size, long quantity) {
        this.client=client;
        this.color=color;
        this.size=size;
        this.quantity=quantity;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(getColor(), cartItem.getColor()) &&
                Objects.equals(getSize(), cartItem.getSize()) &&
                Objects.equals(getClient(), cartItem.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getSize(), getClient());
    }
}
