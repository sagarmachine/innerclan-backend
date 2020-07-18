package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product ,Long> {

    Product findByProductName(String name);

}
