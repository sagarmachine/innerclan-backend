package com.innerclan.v1.service;

import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.ProductNotFoundException;
import com.innerclan.v1.repository.CartItemRepository;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartItemServiceImpl implements ICartItemService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public void addCartItem(String email, long productId) {
       Optional<Client> value1= clientRepository.findByEmail(email);
       if(!value1.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");


        Optional<Product> value2= productRepository.findById(productId);
        if(!value2.isPresent()) throw new ProductNotFoundException("product with id "+productId +"not found");

       Client client=value1.get();
       CartItem cartItem = new CartItem(client,productId);

       Set<CartItem> cartItems= client.getCartItems();

        if (cartItems == null) {
            cartItems = new HashSet<CartItem>();
        }
        if(cartItems.contains(cartItem)){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }
        else {
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);
            cartItems.add(cartItem);
        }

        //client.addCartItem(cartItem);

    }
}
