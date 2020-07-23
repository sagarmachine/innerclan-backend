package com.innerclan.v1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


public interface IBindingErrorService {

    ResponseEntity<String[]> getErrorResponse(BindingResult bindingResult);


}
