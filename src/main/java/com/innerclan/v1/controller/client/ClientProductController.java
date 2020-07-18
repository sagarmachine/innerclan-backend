package com.innerclan.v1.controller.client;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@RestController
@RequestMapping(value="/api/v1/product")
public class ClientProductController {

    @GetMapping(value="/get")


}
