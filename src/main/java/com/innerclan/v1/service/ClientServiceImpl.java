package com.innerclan.v1.service;


import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.ClientResponseDto;
import com.innerclan.v1.dto.LoginDto;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.exception.AuthenticationException;
import com.innerclan.v1.exception.ClientAlreadyExsitException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.security.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> addClient(AddClientDto clientDto) {

        ModelMapper mapper = new ModelMapper();
        Client client = mapper.map(clientDto, Client.class);
        UUID uuid = UUID.randomUUID();
        client.setUuid(uuid.toString());
        client.setVisit(0);
        client.setTotalOrder(0);
        client.setPassword(bCryptPasswordEncoder.encode(clientDto.getPassword()));
        try {
            clientRepository.save(client);
        } catch (Exception ex) {
            throw new ClientAlreadyExsitException("Client with email id " + client.getEmail() + " or phone number " + client.getPhone() + " already exist ");
        }

        UserDetails user =loadUserByUsername(clientDto.getEmail());
        String jwtToken = jwtUtil.generateToken(user);
        HttpHeaders headers= new HttpHeaders();;
        headers.add("Authorization","Bearer "+ jwtToken);
        headers.add("name",clientDto.getFirstName());
        headers.add("email",clientDto.getEmail());
        return new ResponseEntity(mapper.map(clientDto, ClientResponseDto.class), headers, HttpStatus.ACCEPTED);


    }

    @Override
    public ResponseEntity<?> authenticateClient(LoginDto loginDto) {

      Authentication token= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword(),new ArrayList<>())) ;
      if(!token.isAuthenticated()){
   throw  new AuthenticationException("credentials does not match");
      }
        ModelMapper mapper = new ModelMapper();
        UserDetails user =loadUserByUsername(loginDto.getEmail());
        String jwtToken = jwtUtil.generateToken(user);
        HttpHeaders headers= new HttpHeaders();;
        headers.add("Authorization","Bearer "+ jwtToken);
        Client client= clientRepository.findByEmail(loginDto.getEmail()).get();
          client.setLastLoggedIn(new Date());
          client.setVisit(client.getVisit()+1);
          clientRepository.save(client);
        headers.add("name",client.getFirstName());
        headers.add("email",loginDto.getEmail());

        return new ResponseEntity(mapper.map(client, ClientResponseDto.class), headers, HttpStatus.ACCEPTED);


    }
////---- encrypting password
//    private String hashPassword(String plainTextPassword) {
//        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
//    }
//
/////--- Decrypting password
//private void checkPass(String plainPassword, String hashedPassword) {
//    if (BCrypt.checkpw(plainPassword, hashedPassword))
//        System.out.println("The password matches.");
//    else
//        System.out.println("The password does not match.");
//}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Client>  clientOptional = clientRepository.findByEmail(email);
        if(clientOptional.isPresent())
         return  new User(clientOptional.get().getEmail(), clientOptional.get().getPassword(), new ArrayList<>());
        else
             return null;
    }
}
