package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;

import com.innerclan.v1.dto.ClientProductView;

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

import org.springframework.data.domain.Pageable;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;



@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

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


}
