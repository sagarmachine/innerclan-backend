package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //List<Order> findAllByOrderByOrderedOnDate(Pageable page);

    Optional<Order> findByOrderId(String orderId);
}
