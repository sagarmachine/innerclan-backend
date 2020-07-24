package com.innerclan.v1.controller.client;


import com.innerclan.v1.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/v1/client/forgotPassword")
@CrossOrigin(value = {"http://localhost:3001","http://localhost:3000"})
public class ForgotPasswordController {

        @Autowired
        IEmailService emailService;

    @GetMapping("")
    public ResponseEntity<?> changePassword(@RequestParam("email") String email) {
          emailService.changePassword(email);

          return new ResponseEntity<>("PASSWORD RESET LINK SENT SUCCESSFULLY", HttpStatus.ACCEPTED);
    }



}
