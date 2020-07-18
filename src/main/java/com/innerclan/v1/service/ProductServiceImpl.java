package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.ProductNotSavedException;
import com.innerclan.v1.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public void addProduct(AddProductDto addProductDto, MultipartFile file) {


        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(addProductDto, Product.class);
        try {
            file.getBytes();
        } catch (IOException ex) {
            throw new ProductNotSavedException("Try Different Image or Different Image Format");
        }


    }
}
