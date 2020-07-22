package com.innerclan.v1.exceptionhandler;


import com.innerclan.v1.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

      @ExceptionHandler(value = ProductNotSavedException.class)
      public ResponseEntity<String[]> productNotSavedExceptionHandler(ProductNotSavedException ex){

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

    @ExceptionHandler(value = SubsciberAlreadyExistException.class)
    public ResponseEntity<String[]> SubscriberAlreadyExistExceptionHandler(SubsciberAlreadyExistException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = DesignNotSentException.class)
    public ResponseEntity<String[]> DesignNotSentExceptionHandler(DesignNotSentException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = IllegalGenderNameUsedException.class)
    public ResponseEntity<String[]> IllegalGenderNameUsedExceptionHandler(IllegalGenderNameUsedException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ImageNotSavedException.class)
    public ResponseEntity<String[]> imageNotSavedExceptionHandler(ImageNotSavedException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = ProductAlreadyExistException.class)
    public ResponseEntity<String[]> productAlreadyExistExceptionHandler(ImageNotSavedException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<String[]> ProductNotFoundExceptionHandler(ProductNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = CartItemNotFoundException.class)
    public ResponseEntity<String[]> CartItemNotFoundException(CartItemNotFoundException ex){

        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

    }









}
