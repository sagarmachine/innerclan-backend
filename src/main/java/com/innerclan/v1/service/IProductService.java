package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;

import com.innerclan.v1.dto.ClientProductView;

import com.innerclan.v1.dto.AdminProductView;

import com.innerclan.v1.dto.UpdateProductDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Color;
import com.innerclan.v1.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IProductService {




    Color addImage(long colorId, MultipartFile file);

    Set<Color> addColors(long id, HashSet<String> colors);


    List<Product> getProductByCategoryIdOrderByView(long id, Pageable pageable);

    List<Product> getProductByCategoryIdOrderBySale(long id, Pageable pageable);

    List<Product> getProductByCategoryIdOrderByPriceAsc(long id, Pageable pageable);

    List<Product> getProductByCategoryIdOrderByPriceDesc(long id, Pageable pageable);

    AdminProductView addProduct(AddProductDto addProductDto, MultipartFile file, long categoryId);

    AdminProductView updateProduct(UpdateProductDto updateProductDto,MultipartFile file );

    List<ClientProductView> getProductBySearch(String search);

    List<Product> getProductByCategoryIdOrderByDate(long id, Pageable pageable);
}
