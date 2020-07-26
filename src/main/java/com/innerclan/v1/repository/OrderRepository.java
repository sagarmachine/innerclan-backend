package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

     //List<Order> findAllByOrderByOrderedOnDate(Pageable page);

    Optional<Order> findByOrderId(String orderId);


    List<Order> findByStatusOrderByUpdatedOnDesc(String status, Pageable page);

    List<Order> findAllByOrderByUpdatedOnDesc(Pageable pageable);
    //List<Order> findAllByOrderByUpdatedOnDesc(Pageable pageable);


}
