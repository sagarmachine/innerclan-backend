package com.innerclan.v1.repository;

import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


    @Transactional
    void deleteAllByClientEmail(String email);

    Optional<CartItem> findByClientAndColorAndSize(Client client, Color color, String size);

    List<CartItem> findByClient(Client client);
}
