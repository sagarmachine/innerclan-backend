package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false)
    String name;

    @ElementCollection
    @CollectionTable(name="category_information", joinColumns = @JoinColumn(name="category_id") )
    @Column(name = "information")
    List<String> information;

    @OneToMany(mappedBy = "category",cascade = CascadeType.PERSIST)
    Set<Product> products;

    public void addProducts(Product product){
        if(products==null){
            products= new HashSet<Product>();
        }
        products.add(product);
        product.setCategory(this);
    }

}


