package com.innerclan.v1.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginKey {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String loginKey;

}
