package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Order;
import com.innerclan.v1.entity.OrderStatus;
import com.innerclan.v1.exception.IllegalOrderStatus;
import com.innerclan.v1.exception.OrderNotFoundException;
import com.innerclan.v1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;


@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    OrderRepository orderRepository;


    @Override
    public void completeOrder(String email) {

    }

    @Override
    public void createOrder(Client client, double total, double promoDiscount, Address address, Set<CartItemDto> colors, String orderId) {

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

        order.getOrderQuery().getQueries().add(query);

        orderRepository.save(order);

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id+"");
        params.put("query", query);
        return new ResponseEntity<>(params, HttpStatus.ACCEPTED);

    }
}
