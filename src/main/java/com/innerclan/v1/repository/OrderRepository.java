package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Order;
import com.innerclan.v1.entity.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

     //List<Order> findAllByOrderByOrderedOnDate(Pageable page);


long countByStatus(OrderStatus status);

    Optional<Order> findByOrderId(String orderId);


    List<Order> findByStatusOrderByUpdatedOnDesc(OrderStatus status, Pageable page);

    List<Order> findAllByOrderByUpdatedOnDesc(Pageable pageable);
    //List<Order> findAllByOrderByUpdatedOnDesc(Pageable pageable);


}
