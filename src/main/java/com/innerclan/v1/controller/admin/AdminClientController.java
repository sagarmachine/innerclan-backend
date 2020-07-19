package com.innerclan.v1.controller.admin;


import com.innerclan.v1.entity.Client;
import com.innerclan.v1.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/client")
@CrossOrigin(value = {"http://localhost:3001","http://localhost:3000"})
public class AdminClientController {


    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/{page}")
    public List<Client> getClients(@PathVariable("page") int page){

        Pageable pageable = PageRequest.of(page, 20);
      return   clientRepository.findAllByOrderByCreatedOn(pageable);

    }

}
