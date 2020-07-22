package com.innerclan.v1.controller.client;

import com.innerclan.v1.service.IPaytmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {


    @Autowired
    IPaytmService paytmService;

    @PostMapping("/payViaPaytm")
    public void payViaPaytm(){


    }

}
