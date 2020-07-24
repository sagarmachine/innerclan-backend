package com.innerclan.v1.controller.client;


import com.innerclan.v1.service.IEmailService;
import com.innerclan.v1.service.IForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/v1/client/forgotPassword")
@CrossOrigin(value = {"http://localhost:3001","http://localhost:3000"})
public class ForgotPasswordController {

        @Autowired
    IForgotPasswordService forgotPasswordService;

    @GetMapping("")
    public ResponseEntity<?> changePassword(@RequestParam("email") String email) {
        forgotPasswordService.changePassword(email);

          return new ResponseEntity<>("PASSWORD RESET LINK SENT SUCCESSFULLY", HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    public ResponseEntity<?> updatePassword(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("uuid") String uuid) {
        forgotPasswordService.updatePassword(email,password,uuid);

        return new ResponseEntity<>("PASSWORD CHANGED SUCCESSFULLY", HttpStatus.OK);

    }



}
