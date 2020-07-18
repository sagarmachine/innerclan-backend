package com.innerclan.v1.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;


@Service
public class BindingErrorServiceImpl implements IBindingErrorService {
    @Override
    public ResponseEntity<String[]> getErrorResponse(BindingResult bindingResult) {



         List<ObjectError>errosList= bindingResult.getAllErrors();
        String []errorArray= new String[errosList.size()];
         int c=0;
         for (ObjectError error:errosList){
           errorArray[c++]= error.getDefaultMessage();
         }

        return new ResponseEntity<>(errorArray, HttpStatus.BAD_REQUEST);

    }
}
