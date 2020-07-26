package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Access;
import com.innerclan.v1.entity.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo,Long> {


    List<Promo> findAllByOrderByCreatedOnDesc();

    Optional<Promo> findByName(String name);

    List<Promo> findByAccess(Access access);
}
