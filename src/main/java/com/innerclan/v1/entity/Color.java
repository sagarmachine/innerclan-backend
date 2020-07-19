package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String colorName;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JsonIgnore

    Product product;

    @OneToMany(mappedBy = "color",cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    List<Image> images= new ArrayList<>();





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
        images.add(image);
        image.setColor(this);
    }
}
