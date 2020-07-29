package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddCategoryDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Gender;
import com.innerclan.v1.exception.CategoryAlreadyExistException;
import com.innerclan.v1.exception.CategoryNotFoundException;
import com.innerclan.v1.exception.IllegalGenderNameUsedException;
import com.innerclan.v1.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryServiceImpl implements ICategoryService {

@Autowired
    CategoryRepository categoryRepo;



    @Override
    public void addCategory(AddCategoryDto category) {
        ModelMapper mapper = new ModelMapper();
        Category category1 = mapper.map(category, Category.class);

         category1.setName(category1.getName().toUpperCase());
        if(category.getGender().equals("M"))
            category1.setGender(Gender.MALE);
        else if(category.getGender().equals("F"))
            category1.setGender(Gender.FEMALE);
        else if(category.getGender().equals("U"))
            category1.setGender(Gender.UNISEX);
        else
            throw  new IllegalGenderNameUsedException("only 'M' 'F' 'U' as a gender allowed");

        try {
            categoryRepo.save(category1);
        } catch (DataIntegrityViolationException ex) {
            throw new CategoryAlreadyExistException("Same Category Name Already Exist Try A Different Name");
        }

    }


    @Override
    public void updateCategory(AddCategoryDto c) {

        ModelMapper mapper= new ModelMapper();
        Category category = mapper.map(c,Category.class);

        if(c.getGender().equals("M"))
            category.setGender(Gender.MALE);
        else if(c.getGender().equals("F"))
            category.setGender(Gender.FEMALE);
        else if(c.getGender().equals("U"))
            category.setGender(Gender.UNISEX);
        else
            throw  new IllegalGenderNameUsedException("only 'M' 'F' 'U' as a gender allowed");



        category.setName(category.getName().toUpperCase());
        Optional<Category> value = categoryRepo.findById(c.getId());
        if (value.isPresent())
          categoryRepo.save(category);

        else
            throw new CategoryNotFoundException("no category with id "+c.getId()+" found");


    }

    @Override
    public void deleteCategory(int id) {

        try {
            categoryRepo.deleteById((long)id);
        }catch(Exception ex) {
            throw new CategoryNotFoundException("no category with id "+id+" found");
        }


    }
}



