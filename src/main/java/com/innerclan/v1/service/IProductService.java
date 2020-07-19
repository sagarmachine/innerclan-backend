package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.dto.AdminProductView;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Color;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public interface IProductService {

    AdminProductView addProduct(AddProductDto addProductDto, MultipartFile file, long categoryId);

    Color addImage(long colorId, MultipartFile file);

    Set<Color> addColors(long id, HashSet<String> colors);

}
