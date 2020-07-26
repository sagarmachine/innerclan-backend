package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.*;
import com.innerclan.v1.exception.IllegalOrderStatus;
import com.innerclan.v1.exception.OrderNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.OrderRepository;
import com.innerclan.v1.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    OrderRepository orderRepository;


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PromoRepository promoRepository;

    @Override
    public void completeOrder(String orderId,String txnId, String paymentMode) {

        Optional<Order> orderOptional= orderRepository.findByOrderId(orderId);
        if(!orderOptional.isPresent())
            throw  new OrderNotFoundException("no order found with id :"+orderId);

        Order order = orderOptional.get();
        Client client= order.getClient();

        Promo promo= promoRepository.findByName(order.getPromoUsed()).get();

        client.addPromos(promo);
        client.setTotalOrder(client.getTotalOrder()+1);

        order.setTransactionId(txnId);
        order.setPaymentMode(paymentMode);
        order.setStatus(OrderStatus.PLACED);
       // orderRepository.save(order);
        clientRepository.save(client);
    }

    @Override
    public void createOrder(Client client, double total, double promoDiscount, Address address, Set<CartItem> cartItems, String orderId) {

        Order order = new Order();

        order.setClient(client);
        client.addOrder(order);
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        order.setAddress(address);
        order.setOrderId(orderId);
        order.setTotal(total);
        order.setPromoDiscount(promoDiscount);

        List<OrderItem> orderItems= new ArrayList<>();

        for (CartItem cartItem :cartItems){
            OrderItem orderItem= new OrderItem();
            Color color = cartItem.getColor();
            Product product= color.getProduct();

            orderItem.setColor(color.getColorName());
            orderItem.setImage(color.getImages().get(0).getImage());
            orderItem.setProductName(product.getProductName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSize());
            orderItem.setPrice(product.getProductPrice());

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
           clientRepository.save(client);

    }


    @Override
    public void updateOrderStatus(long id, String orderStatus) {

        Optional<Order>orderOptional = orderRepository.findById(id);

        if(!orderOptional.isPresent())
            throw  new OrderNotFoundException("no order foung with id "+id);


        int flag=0;
        OrderStatus status=null;
        for (OrderStatus s:OrderStatus.values()){
            if(s.toString().equals(orderStatus)) {
                flag = 1;
               status=s;
            }
        }
        if(flag==0)
          throw  new IllegalOrderStatus("use a valid status value");

        Order order = orderOptional.get();
        order.setStatus(status);
        orderRepository.save(order);

    }


    @Override
    public ResponseEntity<?> addQuery(long id, String query, String email) {

        Optional<Order> orderOptional= orderRepository.findById(id);

        if(!orderOptional.isPresent() || orderOptional.get().getClient().getEmail().equals(email))
            throw new OrderNotFoundException("no order found");

        Order order = orderOptional.get();

        order.getQueries().add(query);

        orderRepository.save(order);

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id+"");
        params.put("query", query);
        return new ResponseEntity<>(params, HttpStatus.ACCEPTED);

    }
}
