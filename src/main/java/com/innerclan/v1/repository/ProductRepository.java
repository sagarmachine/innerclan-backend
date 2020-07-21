package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    long countByCategoryId(long id);

    Product findByProductName(String name);

    List<Product> findByCategoryIdOrderByViewDesc(long id, Pageable pageable);

    List<Product> findByCategoryIdOrderBySaleDesc(long id, Pageable pageable);

    List<Product> findByProductNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String search1,String search2);

    List<Product> findByCategoryIdOrderByCreatedOnDesc(long id, Pageable pageable);

    List<Product> findAllByOrderByCreatedOnDesc(Pageable pageable);

    List<Product> findByCategoryIdOrderByProductPriceAsc(long id, Pageable pageable);

    List<Product> findByCategoryIdOrderByProductPriceDesc(long id, Pageable pageable);
}