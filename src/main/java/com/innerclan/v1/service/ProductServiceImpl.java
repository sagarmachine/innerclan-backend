package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;

import com.innerclan.v1.dto.ClientProductView;

import com.innerclan.v1.dto.AdminProductView;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;



import java.util.ArrayList;
import java.util.List;

import java.util.Optional;



@Service
@Slf4j

public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProductRepository productRepo;


    @Override

    public List<ClientProductView>  getProductByCategoryId(long id, Pageable pageable) {


        List<Product> products = productRepo.findByCategoryId(id, pageable);
        return getClientProductViews(id, products);
    }

    @Override
    public List<ClientProductView> getProductByCategoryIdOrderByView(long id, Pageable pageable) {



        List<Product> products = productRepo.findByCategoryIdOrderByViewDesc(id, pageable);
        return getClientProductViews(id,  products);
    }

    @Override
    public List<ClientProductView> getProductByCategoryIdOrderBySale(long id, Pageable pageable) {
        List<ClientProductView> result = new ArrayList<>();

        List<Product> products = productRepo.findByCategoryIdOrderBySaleDesc(id, pageable);
        return getClientProductViews(id, products);
    }

    @Override
    public List<ClientProductView> getProductByCategoryIdOrderByPriceAsc(long id, Pageable pageable) {
        List<ClientProductView> result = new ArrayList<>();

        List<Product> products = productRepo.findByCategoryIdOrderByActualPriceAsc(id, pageable);
        return getClientProductViews(id, products);
    }

    @Override
    public List<ClientProductView> getProductByCategoryIdOrderByPriceDesc(long id, Pageable pageable) {
        List<ClientProductView> result = new ArrayList<>();

        List<Product> products = productRepo.findByCategoryIdOrderByActualPriceDesc(id, pageable);
        return getClientProductViews(id, products);
    }


    private List<ClientProductView> getClientProductViews(long id,  List<Product> products) {
        List<ClientProductView> result = new ArrayList<>();
        long size = productRepo.countByCategoryId(id);
        ModelMapper mapper = new ModelMapper();
        for (Product p : products) {
            ClientProductView product = mapper.map(p, ClientProductView.class);
            product.setSize(size);
            result.add(product);
        }

        return result;


    }



    @Override
    public AdminProductView addProduct(AddProductDto addProductDto, MultipartFile file, long categoryId) {

        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(addProductDto, Product.class);

        try {
            log.info("uploading "+file.getOriginalFilename());
            product.setDefaultImage(file.getBytes());
        } catch (IOException ex) {
            throw new ProductNotSavedException("Try Different Image or Different Image Format");
        }
          product.setProductName(product.getProductName().toUpperCase());
        productRepository.save(product);

        product=productRepository.findByProductName(addProductDto.getProductName().toUpperCase());
         return mapper.map(product,AdminProductView.class);

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
            productColor.setColorName(color.toUpperCase());
            productColor.setProduct(product);
            productColors.add(productColor);
        }
        product.setColors(productColors);
        productRepository.save(product);

        return productRepository.findById(id).get().getColors();

    }
}
