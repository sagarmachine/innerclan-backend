package com.innerclan.v1.service;


import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.exception.ClientAlreadyExsitException;
import com.innerclan.v1.repository.ClientRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void addClient(AddClientDto clientDto) {

        ModelMapper mapper = new ModelMapper();
        Client client = mapper.map(clientDto, Client.class);
        UUID uuid = UUID.randomUUID();
        client.setUuid(uuid.toString());
        client.setVisit(0);
        client.setTotalOrder(0);
        client.setNewUser(true);
        client.setPassword(hashPassword(client.getPassword()));
        try {
            clientRepository.save(client);
        } catch (Exception ex) {
            throw new ClientAlreadyExsitException("Client with email id " + client.getEmail() + " or phone number " + client.getPhone() + " already exist ");
        }


    }
//---- encrypting password
    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

///--- Decrypting password
private void checkPass(String plainPassword, String hashedPassword) {
    if (BCrypt.checkpw(plainPassword, hashedPassword))
        System.out.println("The password matches.");
    else
        System.out.println("The password does not match.");
}

}
