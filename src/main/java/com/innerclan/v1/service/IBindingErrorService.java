package com.innerclan.v1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface IBindingErrorService {

    ResponseEntity<String[]> getErrorResponse(BindingResult bindingResult);

}
