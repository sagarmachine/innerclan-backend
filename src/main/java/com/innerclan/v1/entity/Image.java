package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Lob
    @Column(nullable = false)
    byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
     @JsonIgnore
    Color color;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return getId() == image.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

