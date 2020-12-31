package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;

import com.innerclan.v1.dto.ClientProductView;

import com.innerclan.v1.dto.AdminProductView;
import com.innerclan.v1.dto.UpdateProductDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
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


//  @Value("${imgbb.url}")
//    String url;//="https://api.imgbb.com/1/upload?key=dcbdc94a138d3a04d52f008ec67168a5";
String url="https://api.imgbb.com/1/upload?key=dcbdc94a138d3a04d52f008ec67168a5";



    @Override

    public List<Product> getProductByCategoryIdOrderByDate(long id, Pageable pageable) {
        if(id==-1) return productRepository.findAllByOrderByCreatedOnDesc(pageable);
           Optional<Category> value =  categoryRepository.findById(id);
           if(!value.isPresent())
               throw new CategoryNotFoundException("CATEGORY ID "+id+ " IS INVALID INPUT ");

        return productRepository.findByCategoryIdOrderByCreatedOnDesc(id, pageable);

    }

    @Override
    public List<Product>getProductByCategoryIdOrderByView(long id, Pageable pageable) {

        Optional<Category> value =  categoryRepository.findById(id);
        if (!value.isPresent())
            throw new CategoryNotFoundException("CATEGORY ID "+id+ " IS INVALID INPUT ");

        return productRepository.findByCategoryIdOrderByViewDesc(id, pageable);

    }

    @Override
    public List<Product> getProductByCategoryIdOrderBySale(long id, Pageable pageable) {
        Optional<Category> value =  categoryRepository.findById(id);
        if (!value.isPresent()) throw new CategoryNotFoundException("CATEGORY ID "+id+ " IS INVALID INPUT ");

        return productRepository.findByCategoryIdOrderBySaleDesc(id, pageable);

    }

    @Override
    public List<Product>getProductByCategoryIdOrderByPriceAsc(long id, Pageable pageable) {

        Optional<Category> value =  categoryRepository.findById(id);
        if (!value.isPresent()) throw new CategoryNotFoundException("CATEGORY ID "+id+ " IS INVALID INPUT ");

        return productRepository.findByCategoryIdOrderByProductPriceAsc(id, pageable);

    }

    @Override
    public List<Product> getProductByCategoryIdOrderByPriceDesc(long id, Pageable pageable) {

        Optional<Category> value =  categoryRepository.findById(id);
        if (!value.isPresent()) throw new CategoryNotFoundException("CATEGORY ID "+id+ " IS INVALID INPUT ");

        return productRepository.findByCategoryIdOrderByProductPriceDesc(id, pageable);

    }


    @Override
    public List<ClientProductView> getProductBySearch(String search) {
        List<Product> products= productRepository.findByProductNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(search,search);

        List<ClientProductView> result=new ArrayList<>();
        ModelMapper mapper=new ModelMapper();
        for(Product p:products){

            ClientProductView product= mapper.map(p,ClientProductView.class);
            product.setSize(products.size());
            result.add(product);
        }
        return result;

    }


    @Override
    public AdminProductView addProduct(AddProductDto addProductDto, MultipartFile file, long categoryId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(!categoryOptional.isPresent())
            throw new CategoryNotFoundException("no category found with id "+categoryId);


        Category category = categoryOptional.get();

        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(addProductDto, Product.class);


        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            map.add("image",   Base64.getEncoder().encodeToString(file.getBytes()));

        } catch (Exception ex){
            throw new ImageNotSavedException("image was not saved try a different image");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);


        HashMap<String,Object> result= restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class).getBody();
      //  System.out.println((String)((HashMap)result.get("data")).get("display_url"));
        product.setDefaultImage((String)((HashMap)result.get("data")).get("display_url"));
        product.setDeleteImage((String)result.get("delete_url"));


//        try {
//            log.info("uploading "+file.getOriginalFilename());
//            product.setDefaultImage(file.getBytes());
//        } catch (IOException ex) {
//            throw new ProductNotSavedException("Try Different Image or Different Image Format");
//        }
          product.setProductName(product.getProductName().toUpperCase());
        category.addProducts(product);
        try {
            categoryRepository.save(category);
        }catch(DataIntegrityViolationException ex){
            throw  new ProductAlreadyExistException("product with name: "+product.getProductName()+" already exist");
        }
        product=productRepository.findByProductName(addProductDto.getProductName().toUpperCase());
         return mapper.map(product,AdminProductView.class);

    }

    @Override
    public AdminProductView updateProduct(UpdateProductDto updateProductDto, MultipartFile file) {

        Optional<Product> productOptional = productRepository.findById(updateProductDto.getId());

        if(!productOptional.isPresent())
            throw  new ProductNotFoundException("no product with id"+updateProductDto.getId());


        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(updateProductDto, Product.class);


        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            map.add("image",   Base64.getEncoder().encodeToString(file.getBytes()));

        } catch (Exception ex){
            throw new ImageNotSavedException("image was not saved try a different image");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);


        HashMap<String,Object> result= restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class).getBody();
        product.setDefaultImage((String)((HashMap)result.get("data")).get("display_url"));
        product.setDeleteImage((String)result.get("delete_url"));

//        try {
//            log.info("uploading "+file.getOriginalFilename());
//            product.setDefaultImage(file.getBytes());
//        } catch (IOException ex) {
//            throw new ProductNotSavedException("Try Different Image or Different Image Format");
//        }
        product.setView(productOptional.get().getView());
        product.setSale(productOptional.get().getSale());
        product.setCreatedOn(productOptional.get().getCreatedOn());
        product.setProductName(product.getProductName().toUpperCase());
        product.setCategory(productOptional.get().getCategory());

       try {
           productRepository.save(product);
       }catch(DataIntegrityViolationException ex){
           throw new ProductAlreadyExistException("product with same name already exist");
       }
        product=productRepository.findById(updateProductDto.getId()).get();
        return mapper.map(product,AdminProductView.class);

    }




    @Override
    public Color addImage(long colorId, MultipartFile file) {

        Optional<Color> colorOptional = colorRepository.findById(colorId);

        if(!colorOptional.isPresent())
            throw  new ProductNotFoundException("no product with id "+colorId+" found");

        Color color=colorOptional.get();
        Image image= new Image();

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            map.add("image",   Base64.getEncoder().encodeToString(file.getBytes()));

        } catch (Exception ex){
            throw new ImageNotSavedException("image was not saved try a different image");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);


       HashMap<String,Object> result= restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class).getBody();
        image.setImage((String)((HashMap)result.get("data")).get("display_url"));
        image.setDeleteImage((String)result.get("delete_url"));
        color.addImages(image);
        colorRepository.save(color);
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
            productColors.add(productColor);
            product.addColors(productColor);
            colorRepository.save(productColor);
        }
        product.setColors(productColors);
        productRepository.save(product);

        return productRepository.findById(id).get().getColors();

    }
}
