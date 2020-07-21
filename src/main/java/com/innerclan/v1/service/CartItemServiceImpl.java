package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.dto.ClientProductView;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.ProductNotFoundException;
import com.innerclan.v1.repository.CartItemRepository;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartItemServiceImpl implements ICartItemService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public void addCartItem(String email, long productId) {
       Optional<Client> clientValue= clientRepository.findByEmail(email);
       if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");


        Optional<Product> productValue= productRepository.findById(productId);
        if(!productValue.isPresent()) throw new ProductNotFoundException("product with id "+productId +"not found");

       Client client=clientValue.get();
       Optional<CartItem> cartItemValue= cartItemRepository.findByClientAndProductId(client,productId);

        CartItem cartItem;
        if(!cartItemValue.isPresent())
       cartItem=new CartItem(client,productId);

       else cartItem=cartItemValue.get();

       Set<CartItem> cartItems= client.getCartItems();

        if (cartItems == null) {
            cartItems = new HashSet<CartItem>();
        }
        if(cartItems.contains(cartItem)){
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemRepository.save(cartItem);
        }
        else {
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);
            cartItems.add(cartItem);
        }

        //client.addCartItem(cartItem);

    }

    @Override
    public List<CartItemDto> getCartItems(String email) {

        Optional<Client> value = clientRepository.findByEmail(email);
        if(!value.isPresent()) throw new ClientNotFoundException("client with email "+ " doesn't exist, not found");

        return getDtoCartItems(value.get().getCartItems());

    }

    private List<CartItemDto> getDtoCartItems(Set<CartItem> cartItems) {

        List<CartItemDto> result= new ArrayList<CartItemDto>() ;

        ModelMapper mapper = new ModelMapper();
        for(CartItem cartItem:cartItems) {
            Optional<Product> productOptional =productRepository.findById(cartItem.getId());
            ClientProductView clientProductView= mapper.map(productOptional.get(), ClientProductView.class);

            CartItemDto cartItemDto =new CartItemDto(clientProductView,cartItem.getQuantity(),cartItem.getQuantity()*productOptional.get().getProductPrice());
           result.add(cartItemDto);
        }
        return result;
    }
}
