package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignRepository extends JpaRepository<Design,Long> {
    List<Design> findAllByOrderByCreatedOnDesc();

    List<Design> findBySeenOrderByCreatedOnDesc(boolean b);
}
