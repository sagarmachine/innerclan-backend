package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String colorName;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    Product product;

    @OneToMany(mappedBy = "color",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    List<Image> images;





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;
        Color color = (Color) o;
        return getId() == color.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }




    public void addImages(Image image){
        if(images==null){
            images= new ArrayList<Image>();
        }
        images.add(image);
        image.setColor(this);
    }
}
