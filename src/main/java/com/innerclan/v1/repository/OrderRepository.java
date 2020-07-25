package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll(Pageable page);

}
