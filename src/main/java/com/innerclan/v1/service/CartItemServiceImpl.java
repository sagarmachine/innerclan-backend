package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddToCartDto;
import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.dto.ClientProductView;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Color;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.CartItemNotFoundException;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.ColorNotFoundException;
import com.innerclan.v1.exception.ProductNotFoundException;
import com.innerclan.v1.repository.CartItemRepository;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ColorRepository;
import com.innerclan.v1.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
//8375823136
@Service
public class CartItemServiceImpl implements ICartItemService {


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;


    @Autowired
    ColorRepository colorRepository;

    public void addCartItem(String email, AddToCartDto addToCartDto) {
       Optional<Client> clientValue= clientRepository.findByEmail(email);
       if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");
        Client client=clientValue.get();

        Optional<Color> colorRepositoryOptional=colorRepository.findById(addToCartDto.getColorId());
        if(!colorRepositoryOptional.isPresent()) throw new ColorNotFoundException("NO PRODUCT FOUND WITH COLOR ID :"+addToCartDto.getColorId());
         Color color=colorRepositoryOptional.get();

        CartItem cartItem;
       Optional<CartItem> cartItemValue= cartItemRepository.findByClientAndColorAndSize(client,color,addToCartDto.getSize());


        if(!cartItemValue.isPresent()) {
            cartItem = new CartItem(client, color,addToCartDto.getSize(), addToCartDto.getQuantity());

        }
       else {
            cartItem = cartItemValue.get();
        }

       Set<CartItem> cartItems= client.getCartItems();

        if (cartItems == null) {
            cartItems = new HashSet<CartItem>();
        }
        if(cartItems.contains(cartItem)){
            cartItem.setQuantity(addToCartDto.getQuantity());
            cartItemRepository.save(cartItem);
        }
        else {

            cartItemRepository.save(cartItem);

        }



    }

    @Override
    public double addCartItems(String email, List<AddToCartDto> addToCartDtoList) {

        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");
        Client client=clientValue.get();

        double cartTotal = 0;
        int i=1;
        for (AddToCartDto c : addToCartDtoList) {
            Optional<Color> colorRepositoryOptional=colorRepository.findById(c.getColorId());
            if(!colorRepositoryOptional.isPresent()) throw new ColorNotFoundException("NO PRODUCT FOUND WITH COLOR ID :"+c.getColorId());
            Color color=colorRepositoryOptional.get();

            CartItem cartItem=new CartItem(client,color,c.getSize(),c.getQuantity());

            cartItemRepository.save(cartItem);
            log.info(i+"price------------"+color.getProduct().getProductPrice()+"  quantity  "+ c.getQuantity());
            cartTotal += (color.getProduct().getProductPrice() * c.getQuantity());
            log.info("------------"+cartTotal); i++;
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

        List<CartItemDto> result= new ArrayList<>() ;

        for(CartItem c: cartItems){
            CartItemDto cartItemDto=new CartItemDto();
            Product product=c.getColor().getProduct();
            Color color=c.getColor();

            cartItemDto.setSelectedProductId(product.getId());
            cartItemDto.setProductName(product.getProductName());
            cartItemDto.setProductPrice(product.getProductPrice());
            cartItemDto.setSelectedColorId(color.getId());
            cartItemDto.setSelectedColorName(color.getColorName());
            if(color.getImages().size()!=0)
            cartItemDto.setSelectedColorImage(color.getImages().get(0).getImage());
            cartItemDto.setQuantity(c.getQuantity());
            cartItemDto.setSize(c.getSize());


            result.add(cartItemDto);
        }

        return result;
    }



    @Override
    public void deleteCartItem(String email, long colorId, String size) {

        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");
        
       Optional<Color> colorRepositoryOptional=colorRepository.findById(colorId);
       if(!colorRepositoryOptional.isPresent()) throw new ColorNotFoundException("NO PRODUCT FOUND WITH COLOR ID :"+colorId);
       Color color=colorRepositoryOptional.get();


        Client client=clientValue.get();

        Optional<CartItem> cartItemValue= cartItemRepository.findByClientAndColorAndSize(client,color,size);
        if(!cartItemValue.isPresent()) throw new CartItemNotFoundException("Product Not found in the Cart");
        CartItem cartItem = cartItemValue.get();
       // client.getCartItems().remove(cartItem);
        cartItemRepository.deleteById(cartItem.getId());


    }



    @Override
    public void deleteAllCartItems(String email) {

        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");

       // clientValue.get().getCartItems().clear();
        cartItemRepository.deleteAllByClientEmail(email);

    }

    @Override
    public double getCartTotal(String email) {
        double cartTotal = 0;
        int i=1;

        Optional<Client> value = clientRepository.findByEmail(email);
        if(!value.isPresent()) throw new ClientNotFoundException("client with email "+ " doesn't exist, not found");
        Set<CartItem> cartItemList= value.get().getCartItems();
        for (CartItem c : cartItemList){
            Optional<Color> colorRepositoryOptional=colorRepository.findById(c.getColor().getId());
            if(!colorRepositoryOptional.isPresent()) throw new ColorNotFoundException("NO PRODUCT FOUND WITH COLOR ID :"+c.getColor().getId());
            Color color=colorRepositoryOptional.get();


            log.info(i+"price------------"+color.getProduct().getProductPrice()+"  quantity  "+ c.getQuantity());
            cartTotal += (color.getProduct().getProductPrice() * c.getQuantity());
            log.info("------------"+cartTotal); i++;

        }
        return cartTotal;
    }


}
