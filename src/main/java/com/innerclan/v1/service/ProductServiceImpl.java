package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.dto.AdminProductView;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.CategoryNotFoundException;
import com.innerclan.v1.exception.ProductAlreadyExistException;
import com.innerclan.v1.exception.ProductNotSavedException;
import com.innerclan.v1.repository.CategoryRepository;
import com.innerclan.v1.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public AdminProductView addProduct(AddProductDto addProductDto, MultipartFile file,long categoryId) {


        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        Category category=null;

        if(categoryOptional.isPresent())
               category=categoryOptional.get();
        else
           throw  new CategoryNotFoundException("no category with id "+categoryId+" found");


        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(addProductDto, Product.class);
        try {
           product.setDefaultImage(file.getBytes());
        } catch (IOException ex) {
            throw new ProductNotSavedException("Try Different Image or Different Image Format");
        }

          category.addProducts(product);

        try {
           categoryRepository.save(category);
        }catch(DataIntegrityViolationException ex){
            throw new ProductAlreadyExistException("product with same name already exist");
        }

        product=productRepository.findByProductName(addProductDto.getName());

        AdminProductView adminProductView= mapper.map(product,AdminProductView.class);

        return adminProductView;

    }
}
