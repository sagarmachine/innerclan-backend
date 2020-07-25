package com.innerclan.v1.controller.client;


import com.innerclan.v1.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1/order")
public class ClientOrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping("/addQuery/{id}")
    public ResponseEntity<?> addQuery(@PathVariable("id") long id, @RequestParam("query")  String query, Principal principal){

    return    orderService.addQuery(id,query,principal.getName());

    }

}
