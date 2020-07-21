package com.innerclan.v1.repository;

import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByClientAndProductId(Client client, long productId);
}
