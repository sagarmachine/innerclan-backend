package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IClientService extends UserDetailsService{
   ResponseEntity<?> addClient(AddClientDto clientDto);
}
