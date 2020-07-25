package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AdminOrderViewDto;
import com.innerclan.v1.entity.Order;
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

@RestController
@RequestMapping(value = "/api/v1/admin/order")
public class AdminOrderController {


    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    IOrderService orderService;

    @GetMapping("")
    ResponseEntity<?> getOrders(Principal principal,int pageNumber){

        Pageable pageable = PageRequest.of((int) pageNumber, 10);
        List<AdminOrderViewDto> adminOrderView= new ArrayList<>();

        ModelMapper mapper= new ModelMapper();

        for (Order order : orderRepository.findAll(pageable)){
            AdminOrderViewDto adminOrderViewDto= new AdminOrderViewDto();
            adminOrderViewDto= mapper.map(order,AdminOrderViewDto.class);
            adminOrderViewDto.setEmail(order.getClient().getEmail());
            adminOrderView.add(adminOrderViewDto);
        }

       return new ResponseEntity<>(adminOrderView, HttpStatus.OK);

    }

    @PutMapping("/updateOrderStatus/{id}/{status}")
    void updateOrderStatus(@PathVariable("id")long id, @PathVariable("status")String status){
       
        orderService.updateOrderStatus(id, status);
    }


}
