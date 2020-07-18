package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddCategoryDto;
import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Gender;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.CategoryNotSavedException;
import com.innerclan.v1.exception.CategoryNotUpdatedException;
import com.innerclan.v1.exception.ProductNotSavedException;
import com.innerclan.v1.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements ICategoryService {

@Autowired
    CategoryRepository categoryRepo;



    @Override
    public void addCategory(AddCategoryDto category) {
        ModelMapper mapper = new ModelMapper();
        Category category1 = mapper.map(category, Category.class);


        if(category.getGender().equals("M")) category1.setGender(Gender.MALE);
        else if(category.getGender().equals("F")) category1.setGender(Gender.FEMALE);
        else category1.setGender(Gender.UNISEX);

        try {
            categoryRepo.save(category1);
        } catch (DataIntegrityViolationException ex) {
            throw new CategoryNotSavedException("Same Category Name Already Exist Try With Some Other Name");
        }


    }


    @Override
    public void updateCategory(AddCategoryDto c) {

        ModelMapper mapper= new ModelMapper();
        Category category = mapper.map(c,Category.class);

        Optional<Category> value = categoryRepo.findById((int)c.getId());
        if (value.isPresent()) {

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



