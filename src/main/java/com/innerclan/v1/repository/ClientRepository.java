package com.innerclan.v1.repository;

import com.innerclan.v1.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository <Client,Long> {


    //List<Client> findAllByOrderByCreatedOn(Pageable pageable);

    List<Client> findAllByOrderByCreatedOn();


    Optional<Client> findByEmail(String email);
}
