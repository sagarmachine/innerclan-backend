package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.dto.AdminProductView;
import com.innerclan.v1.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface IProductService {

    AdminProductView addProduct(AddProductDto addProductDto, MultipartFile file, long categoryId);


}
