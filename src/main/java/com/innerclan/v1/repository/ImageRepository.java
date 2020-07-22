package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
