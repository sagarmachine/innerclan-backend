package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.*;
import com.innerclan.v1.exception.IllegalOrderStatus;
import com.innerclan.v1.exception.OrderNotFoundException;
import com.innerclan.v1.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {


    @Autowired
    OrderRepository orderRepository;


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PromoRepository promoRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void completeOrder(String orderId,String txnId, String paymentMode) {

        log.info("PLACING ORDER-------------------------------------->");

        Optional<Order> orderOptional= orderRepository.findByOrderId(orderId);
        if(!orderOptional.isPresent())
            throw  new OrderNotFoundException("no order found with id :"+orderId);

        Order order = orderOptional.get();
        Client client= order.getClient();


         if(order.getPromoUsed()!=null)
         {Optional<Promo> promoOptional= promoRepository.findByName(order.getPromoUsed());
                 if(promoOptional.isPresent())
           client.addPromos(promoOptional.get());}
        client.setTotalOrder(client.getTotalOrder()+1);
         //----- incrementing product sale;
        List<CartItem> cartItemList= cartItemRepository.findByClient(client);
        Set<Long> saleCounter =new HashSet<>();
        for(CartItem c:cartItemList){
            Product p=c.getColor().getProduct();
            if(saleCounter.add(p.getId())) {
                p.setSale(p.getSale()+1);
                productRepository.save(p);
            }
        }
        cartItemRepository.deleteAllByClientEmail(client.getEmail());
        //client.setCartItems(new HashSet<>());


        order.setTransactionId(txnId);
        order.setPaymentMode(paymentMode);
        order.setStatus(OrderStatus.PLACED);
        orderRepository.save(order);
        clientRepository.save(client);
    }


    @Override
    public void orderFailed(String orderId) {
        Optional<Order> orderOptional= orderRepository.findByOrderId(orderId);
        if(!orderOptional.isPresent())
            throw  new OrderNotFoundException("no order found with id :"+orderId);

        Order order = orderOptional.get();
        order.setStatus(OrderStatus.PAYMENT_FAILED);
    }


    @Override
    public void createOrder(Client client, double total, double promoDiscount,String promoUsed, Address address, Set<CartItem> cartItems, String orderId) {

        Order order = new Order();

        order.setClient(client);
        client.addOrder(order);
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        order.setAddress(address);
        order.setOrderId(orderId);
        order.setTotal(total);
        order.setPromoDiscount(promoDiscount);

        if(promoDiscount>0)
            order.setPromoUsed(promoUsed);


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
            throw  new OrderNotFoundException("no order found with id "+id);


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
             log.info(!orderOptional.isPresent()+"");
             log.info( orderOptional.get().getClient().getEmail().equals(email)+"");

        if(!orderOptional.isPresent() || !orderOptional.get().getClient().getEmail().equals(email))
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
