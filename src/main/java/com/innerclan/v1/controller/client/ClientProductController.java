package com.innerclan.v1.controller.client;


import com.innerclan.v1.dto.AddCategoryDto;
import com.innerclan.v1.dto.ClientProductFullView;
import com.innerclan.v1.dto.ClientProductView;
import com.innerclan.v1.entity.Color;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.repository.ProductRepository;
import com.innerclan.v1.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@RestController
@RequestMapping(value="/api/v1/product")
public class ClientProductController {

    @Autowired
    IProductService productService;

    @Autowired
    ProductRepository productRepository;


    @GetMapping(value="/getProductsOrderByDate/{id}/{page}")
    public ResponseEntity<?> getProductByCategory(@PathVariable("id") long id,@PathVariable("page") long page){

        Pageable pageable = PageRequest.of((int) page, 8);

        List<Product> products= productService.getProductByCategoryIdOrderByDate(id, pageable);

        List<ClientProductView> clientProductViews = getClientProductViews(id,products);

        return new ResponseEntity<>(clientProductViews, HttpStatus.OK);

    }

    @GetMapping(value="/getProductByView/{id}/{page}")
    public ResponseEntity<?> getProductByView(@PathVariable("id") long id,@PathVariable("page") long page){

        Pageable pageable = PageRequest.of((int) page, 8);

        List<Product> products= productService.getProductByCategoryIdOrderByView(id, pageable);
        List<ClientProductView> clientProductViews = getClientProductViews(id,products);

        return new ResponseEntity<>(clientProductViews, HttpStatus.OK);

    }

    @GetMapping(value="/getProductByBestSelling/{id}/{page}")
    public ResponseEntity<?> getProductBySale(@PathVariable("id") long id,@PathVariable("page") long page){

        Pageable pageable = PageRequest.of((int) page, 8);

        List<Product> products= productService.getProductByCategoryIdOrderBySale(id, pageable);
        List<ClientProductView> clientProductViews = getClientProductViews(id,products);

        return new ResponseEntity<>(clientProductViews, HttpStatus.OK);

    }

    @GetMapping(value="/getProductByPriceAsc/{id}/{page}")
    public ResponseEntity<?> getProductByPriceAsc(@PathVariable("id") long id,@PathVariable("page") long page){

        Pageable pageable = PageRequest.of((int) page, 8);

        List<Product>products= productService.getProductByCategoryIdOrderByPriceAsc(id, pageable);

        List<ClientProductView> clientProductViews = getClientProductViews(id,products);

        return new ResponseEntity<>(clientProductViews, HttpStatus.OK);

    }

    @GetMapping(value="/getProductByPriceDesc/{id}/{page}")
    public ResponseEntity<?> getProductByPriceDesc(@PathVariable("id") long id,@PathVariable("page") long page){

        Pageable pageable = PageRequest.of((int) page, 8);

        List<Product> products= productService.getProductByCategoryIdOrderByPriceDesc(id, pageable);


        List<ClientProductView> clientProductViews = getClientProductViews(id,products);

        return new ResponseEntity<>(clientProductViews, HttpStatus.OK);

    }

    private List<ClientProductView> getClientProductViews(long id,  List<Product> products) {
        List<ClientProductView> result = new ArrayList<>();
        long size = productRepository.countByCategoryId(id);
        if(id==-1) size=productRepository.count();
        ModelMapper mapper = new ModelMapper();
        for (Product p : products) {
            ClientProductView product = mapper.map(p, ClientProductView.class);
            product.setSize(size);
            result.add(product);
        }

        return result;

    }


    @GetMapping(value="/getProductsBySearch")
    public ResponseEntity<?> getProductBySearch(@RequestParam("search") String search){

        List<ClientProductView> products= productService.getProductBySearch(search);

        return new ResponseEntity<>(products, HttpStatus.OK);

    }



//--------- Client Full Product View------------
    @GetMapping(value="/getProduct/{id}")
    public ResponseEntity<?> getFullProduct(@PathVariable("id") long id){



         Optional<Product> value= productRepository.findById(id);
         if(value.isPresent()) {
             Product p = value.get();
             ModelMapper mapper = new ModelMapper();
             p.setView(p.getView()+1);
             ClientProductFullView product  = mapper.map(p,  ClientProductFullView .class);

             return new ResponseEntity<>(product, HttpStatus.OK);
         }


        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);


    }





    }
