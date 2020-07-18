package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.CategoryNotSavedException;
import com.innerclan.v1.exception.CategoryNotUpdatedException;
import com.innerclan.v1.exception.ProductNotSavedException;
import com.innerclan.v1.repository.CategoryRepository;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;


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

    @Override
    public void addCategory(Category category) {
      //  category.ge

        // if(((String)category.getGender()).equals("M")) category.setGender(Gender.MALE);
        //else if(((String)category.getGender()).equals("F")) category.setGender(Gender.FEMALE);
        //else category.setGender(Gender.UNISEX);
        ModelMapper mapper = new ModelMapper();
        Category category1 = mapper.map(category, Category.class);

        try {
            categoryRepo.save(category1);
        } catch (DataIntegrityViolationException ex) {
            throw new CategoryNotSavedException("Same Category Name Already Exist Try With Some Other Name");
        }


    }


    @Override
    public void updateCategory(int id, Category c) {

        Optional<Category> value = categoryRepo.findById(id);
        if (value.isPresent()) {
            c.setId(id);
            try {
                categoryRepo.save(c);
            } catch (DataIntegrityViolationException ex) {

                throw new CategoryNotUpdatedException("Same Category Name Already Exists ");
            }


        }
    }

    @Override
    public void deleteCategory(int id) {

            try {
                categoryRepo.deleteById(id);
            }catch(Exception ex) {
                throw new RuntimeException("Category Not deleted");
            }


        }
    }

