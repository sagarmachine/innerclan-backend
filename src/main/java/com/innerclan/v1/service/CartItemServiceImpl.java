package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.dto.ClientProductView;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.CartItemNotFoundException;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.ProductNotFoundException;
import com.innerclan.v1.repository.CartItemRepository;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j

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
//            cartItems.add(cartItem);

        }

        //client.addCartItem(cartItem);

    }

    @Override
    public double addCartItems(String email, List<CartItem> cartItemList) {
        double cartTotal = 0;
        int i=1;
        for (CartItem c : cartItemList) {

            Optional<Product> productOptional = productRepository.findById(c.getProductId());
            if(!productOptional.isPresent()) throw new ProductNotFoundException("PRODUCT WIT ID "+c.getProductId() +" NOT FOUND");
            cartItemRepository.save(c);
            log.info("price------------"+productOptional.get().getProductPrice()+"  quantity  "+ c.getQuantity());
            cartTotal += (productOptional.get().getProductPrice() * c.getQuantity());
            log.info(i+"------------"+cartTotal); i++;
        }
        return cartTotal;
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
            Optional<Product> productOptional =productRepository.findById(cartItem.getProductId());
            ClientProductView clientProductView= mapper.map(productOptional.get(), ClientProductView.class);

            CartItemDto cartItemDto =new CartItemDto(clientProductView,cartItem.getQuantity(),cartItem.getQuantity()*productOptional.get().getProductPrice());
            result.add(cartItemDto);
        }
        return result;
    }

    @Override
    public void deleteCartItem(String email, long productId) {

        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");


        Optional<Product> productValue= productRepository.findById(productId);
        if(!productValue.isPresent()) throw new ProductNotFoundException("product with id "+productId +"not found");

        Client client=clientValue.get();

        Optional<CartItem> cartItemValue= cartItemRepository.findByClientAndProductId(client,productId);
        if(!cartItemValue.isPresent()) throw new CartItemNotFoundException("Product Not found in the Cart");
        CartItem cartItem = cartItemValue.get();
        //client.getCartItems().remove(cartItem);
        cartItemRepository.deleteById(cartItem.getId());




    }

    @Override
    public void deleteAllCartItems(String email) {
        cartItemRepository.deleteAllByClientEmail(email);

    }




}
