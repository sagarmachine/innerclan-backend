package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import org.springframework.web.multipart.MultipartFile;

public interface IProuctService {

    void addProduct(AddProductDto addProductDto, MultipartFile file);

}
