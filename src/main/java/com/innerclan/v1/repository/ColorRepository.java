package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color,Long> {

    Color findByColorNameAndProductId(String name, long id);
}
