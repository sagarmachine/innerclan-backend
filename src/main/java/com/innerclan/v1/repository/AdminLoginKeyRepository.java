package com.innerclan.v1.repository;

import com.innerclan.v1.entity.AdminLoginKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminLoginKeyRepository extends JpaRepository<AdminLoginKey,Integer> {

    Optional<AdminLoginKeyRepository> findByLoginKey(String key);
}
