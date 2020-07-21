package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@JsonIgnoreProperties(value = {"image"})
public class Design {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;

    @Column(nullable=false)
    String email;

    @Column(nullable=false)
    String comment;

    @Lob
    @Column(nullable=false)
    byte[] image;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date createdOn;

    @Column(columnDefinition="bit default 0")
    boolean seen;

    public Design(String email, String comment) {
        this.email=email;
        this.comment=comment;
    }
}
