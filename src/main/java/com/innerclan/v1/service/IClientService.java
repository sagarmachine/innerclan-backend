package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IClientService extends UserDetailsService{

   ResponseEntity<?> addClient(AddClientDto clientDto);
   ResponseEntity<?> authenticateClient(LoginDto loginDto);

}
