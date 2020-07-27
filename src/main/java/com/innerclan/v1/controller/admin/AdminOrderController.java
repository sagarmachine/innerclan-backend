package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AdminOrderViewDto;
import com.innerclan.v1.entity.Order;
import com.innerclan.v1.entity.OrderStatus;
import com.innerclan.v1.exception.IllegalOrderStatus;
import com.innerclan.v1.exception.OrderNotFoundException;
import com.innerclan.v1.repository.OrderRepository;
import com.innerclan.v1.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/admin/order")
public class AdminOrderController {


    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    IOrderService orderService;

    @GetMapping("")
    ResponseEntity<?> getOrders(@RequestParam("pageNumber") int pageNumber){

        Pageable pageable = PageRequest.of((int) pageNumber, 3);
      //  List<AdminOrderViewDto> adminOrderView= new ArrayList<>();
        //ModelMapper mapper= new ModelMapper();

//        for (Order order : orderRepository.findAllByOrderByUpdatedOnDesc(pageable)){
//            AdminOrderViewDto adminOrderViewDto= new AdminOrderViewDto();
//            adminOrderViewDto= mapper.map(order,AdminOrderViewDto.class);
//            adminOrderViewDto.setEmail(order.getClient().getEmail());
//            adminOrderView.add(adminOrderViewDto);
//        }

       return new ResponseEntity<>(orderRepository.findAllByOrderByUpdatedOnDesc(pageable), HttpStatus.OK);

    }



    @GetMapping("/status/{status}")
    ResponseEntity<?> getOrdersByStatus(@PathVariable("status")String s,Principal principal,int pageNumber){


        int flag=0;
        OrderStatus status=null;
        for (OrderStatus st:OrderStatus.values()){
            if(s.toString().equals(s)) {
                flag = 1;
                status=st;
            }
        }
        if(flag==0)
            throw  new IllegalOrderStatus("use a valid status value");


        Pageable pageable = PageRequest.of((int) pageNumber, 10);
        List<AdminOrderViewDto> adminOrderView= new ArrayList<>();

        ModelMapper mapper= new ModelMapper();

        for (Order order : orderRepository.findByStatusOrderByUpdatedOnDesc(status.toString(),pageable)){
            AdminOrderViewDto adminOrderViewDto= new AdminOrderViewDto();
            adminOrderViewDto= mapper.map(order,AdminOrderViewDto.class);
            adminOrderViewDto.setEmail(order.getClient().getEmail());
            adminOrderView.add(adminOrderViewDto);
        }

        return new ResponseEntity<>(adminOrderView, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailView(@PathVariable("id")long id){
        Optional<Order>orderOptional=orderRepository.findById(id);
        if(!orderOptional.isPresent())
            throw  new OrderNotFoundException("no order found with id "+ id);

        return new ResponseEntity<>(orderOptional.get(),HttpStatus.OK);

    }



    @PutMapping("/updateOrderStatus/{id}/{status}")
    void updateOrderStatus(@PathVariable("id")long id, @PathVariable("status")String status){
       
        orderService.updateOrderStatus(id, status);
    }


}
