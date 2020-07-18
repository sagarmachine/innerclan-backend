package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddCategoryDto;
import com.innerclan.v1.entity.Category;

public interface ICategoryService {

    void addCategory( AddCategoryDto category);

    void updateCategory(Category c);

    void deleteCategory(int id);
}
