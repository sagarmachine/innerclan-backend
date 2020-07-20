package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromoRepository extends JpaRepository<Promo,Long> {


    List<Promo> findAllByOrderByCreatedOnDesc();
}
