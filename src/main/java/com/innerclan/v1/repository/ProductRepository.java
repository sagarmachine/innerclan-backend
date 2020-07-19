package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryId(long id, Pageable pageable);

    long countByCategoryId(long id);

    Product findByProductName(String name);

    List<Product> findByCategoryIdOrderByViewDesc(long id, Pageable pageable);

    List<Product> findByCategoryIdOrderBySaleDesc(long id, Pageable pageable);

    List<Product> findByCategoryIdOrderByActualPriceAsc(long id, Pageable pageable);

    List<Product> findByCategoryIdOrderByActualPriceDesc(long id, Pageable pageable);

}