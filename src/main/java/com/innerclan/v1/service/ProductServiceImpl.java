package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.dto.AdminProductView;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Color;
import com.innerclan.v1.entity.Image;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.exception.*;
import com.innerclan.v1.repository.CategoryRepository;
import com.innerclan.v1.repository.ColorRepository;
import com.innerclan.v1.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ColorRepository colorRepository;

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
            log.info("uploading "+file.getOriginalFilename());
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

        product=productRepository.findByProductName(addProductDto.getProductName());

        AdminProductView adminProductView= mapper.map(product,AdminProductView.class);

        return adminProductView;

    }

    @Override
    public Color addImage(long colorId, MultipartFile file) {

        Optional<Color> colorOptional = colorRepository.findById(colorId);

        if(!colorOptional.isPresent())
            throw  new ProductNotFoundException("no product with id "+colorId+" found");

        Color color=colorOptional.get();
        Image image= new Image();

        try {
            image.setImage(file.getBytes());
            color.addImages(image);
            colorRepository.save(color);

   }
        catch (Exception ex){
    throw new ImageNotSavedException("image was not saved try a different image");
}
        return colorRepository.findById(colorId).get();
    }

    @Override
    public Set<Color> addColors(long id, HashSet<String> colors) {

        Optional<Product> productOptional = productRepository.findById(id);
        if(!productOptional.isPresent())
            throw  new ProductNotFoundException("no product found with id "+id);
        Product product= productOptional.get();
        HashSet<Color> productColors= new HashSet<>();
        for (String color :colors){
            Color productColor= new Color();
            productColor.setColorName(color);
            productColor.setProduct(product);
            productColors.add(productColor);
        }
        product.setColors(productColors);
        productRepository.save(product);

        return productRepository.findById(id).get().getColors();

    }
}
