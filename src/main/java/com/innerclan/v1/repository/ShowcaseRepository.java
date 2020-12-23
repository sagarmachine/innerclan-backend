package com.innerclan.v1.repository;

import com.innerclan.v1.dto.ShowCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowcaseRepository extends JpaRepository<ShowCase,Long> {
    List<ShowCase> findAllByOrderByCreatedOnDesc();
}
