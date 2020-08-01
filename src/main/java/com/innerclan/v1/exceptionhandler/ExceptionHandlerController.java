package com.innerclan.v1.exceptionhandler;


import com.innerclan.v1.entity.Order;
import com.innerclan.v1.exception.*;
import com.innerclan.v1.repository.PromoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<String[]> AuthenticationException(AuthenticationException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value= CategoryAlreadyExistException.class)

    public ResponseEntity<String[]> categoryAlreadyExistExceptionHandler(CategoryAlreadyExistException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value= CategoryNotFoundException.class)
    public ResponseEntity<String[]> categoryNotFoundExceptionHandler(CategoryNotFoundException ex){


        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = ClientAlreadyExsitException.class)
    public ResponseEntity<String[]> ClientAlreadyExistException(ClientAlreadyExsitException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<String[]> ClientNotFoundException(ClientNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = ColorNotFoundException.class)
    public ResponseEntity<String[]> colorNotFoundExceptionHandler(ColorNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = CartItemNotFoundException.class)
    public ResponseEntity<String[]> CartItemNotFoundException(CartItemNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = DesignNotFoundException.class)
    public ResponseEntity<String[]> DesignNotFoundExceptionr(DesignNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(value = DesignNotSentException.class)
    public ResponseEntity<String[]> DesignNotSentExceptionHandler(DesignNotSentException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = EmailNotSentToAllSubscription.class)
    public ResponseEntity<String[]> EmailNotSentToAllSubscription(EmailNotSentToAllSubscription ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = IllegalForgotPasswordRequestException.class)
    public ResponseEntity<String[]> IllegalForgotPasswordRequestException(IllegalForgotPasswordRequestException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = IllegalGenderNameUsedException.class)
    public ResponseEntity<String[]> IllegalGenderNameUsedExceptionHandler(IllegalGenderNameUsedException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = IllegalOrderStatus.class)
    public ResponseEntity<String[]> IllegalOrderStatusHandler(IllegalOrderStatus ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ImageNotFoundException.class)
    public ResponseEntity<String[]> ImageNotFoundException(ImageNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = ImageNotSavedException.class)
    public ResponseEntity<String[]> imageNotSavedExceptionHandler(ImageNotSavedException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<String[]> OrderNotFoundExceptionHandler(OrderNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = ProductAlreadyExistException.class)
    public ResponseEntity<String[]> productAlreadyExistExceptionHandler(ProductAlreadyExistException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<String[]> productNotFoundExceptionHandler(ProductNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(value = ProductNotSavedException.class)
      public ResponseEntity<String[]> productNotSavedExceptionHandler(ProductNotSavedException ex){

       return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

      }

    @ExceptionHandler(value = PromoAlreadyExistException.class)
    public ResponseEntity<String[]> PromoAlreadyExistExceptionHandler(PromoAlreadyExistException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = PromoNotFoundException.class)
    public ResponseEntity<String[]> PromoNotFoundExceptionHandler(PromoNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(value = SubsciberAlreadyExistException.class)
    public ResponseEntity<String[]> SubscriberAlreadyExistExceptionHandler(SubsciberAlreadyExistException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


}
