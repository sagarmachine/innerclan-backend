package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/client")
@CrossOrigin(value = {"http://localhost:3001","http://localhost:3000"})
public class AdminClientController {


    @Autowired
    ClientRepository clientRepository;



    @GetMapping("")
    public ResponseEntity<?> getClients() {

        return new ResponseEntity<>(clientRepository.findAllByOrderByCreatedOn(), HttpStatus.OK);

    }



/*
    @GetMapping("/{page}")
    public List<Client> getClients(@PathVariable("page") int page){

        Pageable pageable = PageRequest.of(page, 20);
      return  clientRepository.findAllOrderByCreatedOn(pageable);

    }

 */



}
