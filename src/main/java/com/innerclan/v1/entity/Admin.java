package com.innerclan.v1.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String secret;

    @Column(name = "`key`")
    String key;

    public List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
              authorities.add(new SimpleGrantedAuthority("ADMIN"));
             return authorities;
    }

}
