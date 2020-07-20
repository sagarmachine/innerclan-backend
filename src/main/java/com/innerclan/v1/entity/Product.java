package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Slf4j
@Entity
@Getter @Setter
 @NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false,unique = true)
    String productName;

    @Column(nullable = false)
    double productPrice;

    @Column(nullable = false)
    double actualPrice;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date createdOn;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date updatedOn;

    String comment;


    long view;

    @Lob
    byte[] defaultImage;

    long sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    Category category;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.ALL})
    Set<Color> colors;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId() == product.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }





    public void addColors(Color color){
        if(colors==null){
            colors= new HashSet<Color>();
        }
        colors.add(color);
        color.setProduct(this);
    }

}
