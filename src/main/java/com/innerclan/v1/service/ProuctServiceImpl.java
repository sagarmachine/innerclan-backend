package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.ProductNotSavedException;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ProuctServiceImpl implements IProuctService {


    @Override
    public void addProduct(AddProductDto addProductDto, MultipartFile file) {


        ModelMapper mapper= new ModelMapper();
        Product product =   mapper.map(addProductDto,Product.class);
        try{
            file.getBytes();
     }
     catch(IOException ex){
           throw  new ProductNotSavedException("try different image or different image formatt");
     }


    }
}
