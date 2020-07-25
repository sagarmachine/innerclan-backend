package com.innerclan.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ElementCollection
    @CollectionTable(name = "order_queries",joinColumns = @JoinColumn(name = "order_query_id"))
    List<String> queries;

    @OneToOne
    @JsonIgnore
    Order order;



}
