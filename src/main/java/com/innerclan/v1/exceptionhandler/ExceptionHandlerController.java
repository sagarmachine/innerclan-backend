package com.innerclan.v1.exceptionhandler;


import com.innerclan.v1.exception.ProductNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

      @ExceptionHandler(value = ProductNotSavedException.class)
      public ResponseEntity<String[]> productNotSavedExceptionHandler(ProductNotSavedException ex){

       return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);

      }

//      @ExceptionHandler(value= CategoryNotSavedException.class)
//
//      public ResponseEntity<String[]> productNotSavedExceptionHandler(CategoryNotSavedException ex){
//
//          return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);
//
//      }


//      @ExceptionHandler(value= CategoryNotUpdatedException.class)
//      public ResponseEntity<String[]> productNotUpdatedExceptionHandler(CategoryNotUpdatedException ex){
//
//        return    new ResponseEntity<>(new String[]{ex.getMessage()}, HttpStatus.BAD_REQUEST);
//
//      }



}
