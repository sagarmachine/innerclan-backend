package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.dto.AdminProductView;
import com.innerclan.v1.dto.UpdateProductDto;
import com.innerclan.v1.entity.Color;
import com.innerclan.v1.entity.Product;
import com.innerclan.v1.repository.ProductRepository;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashSet;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/admin/product")
@CrossOrigin(value = {"http://localhost:3001","http://localhost:3000"})
public class AdminProductController {


    @Autowired
    IBindingErrorService bindingErrorService;

    @Autowired
    IProductService productService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "/addProduct/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public  ResponseEntity<?> addProduct(@PathVariable("id") long categoryId,
                                         @RequestParam("productName")String productName,
                                         @RequestParam("productPrice")double productPrice,
                                         @RequestParam("actualPrice")double actualPrice,
                                         @RequestParam("comment")String comment,
                                         @RequestBody MultipartFile file){
AddProductDto addProductDto= new AddProductDto(productName,productPrice,actualPrice,comment);
      return new ResponseEntity<>( productService.addProduct(addProductDto,file,categoryId), HttpStatus.OK);
    }


    @PostMapping(value = "/updateProduct/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public  ResponseEntity<?> updateProduct(
                                            @RequestParam("id")long id,
                                           @RequestParam("productName")String productName,
                                           @RequestParam("productPrice")double productPrice,
                                            @RequestParam("actualPrice")double actualPrice,
                                            @RequestParam("comment")String comment,
                                            @RequestBody MultipartFile file){
        UpdateProductDto productDto= new UpdateProductDto(productName,productPrice,actualPrice,comment,id);
        return new ResponseEntity<>( productService.updateProduct(productDto,file), HttpStatus.OK);
    }

    @PostMapping(value = "/addColors/{id}")
    public ResponseEntity<Set<Color>>  addColors(@PathVariable("id") long productId, @RequestBody List<String> colorList){
        HashSet<String> colors= new HashSet<>(colorList);
       return  ResponseEntity.ok().body(productService.addColors(productId,colors));
    }


    @PostMapping(value="addColorImage/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Color> addImage( @PathVariable("id")long colorId,@RequestBody  MultipartFile file) {

        return new ResponseEntity<>(productService.addImage(colorId, file), HttpStatus.ACCEPTED);

    }
 //-------------------- Getting Products

        @GetMapping(value="/getProductsOrderByDate/{id}/{page}")
        public ResponseEntity<?> getProductByCategoryOrderByDate(@PathVariable("id") long id,@PathVariable("page") long page){

            Pageable pageable = PageRequest.of((int) page, 20);

            List<Product> products= productService.getProductByCategoryIdOrderByDate(id, pageable);

            List<AdminProductView> adminProductViews = getAdminProductViews(id,products);

            return new ResponseEntity<>(adminProductViews, HttpStatus.OK);

        }

        @GetMapping(value="/getProductByView/{id}/{page}")
        public ResponseEntity<?> getProductByView(@PathVariable("id") long id,@PathVariable("page") long page){

            Pageable pageable = PageRequest.of((int) page, 20);

            List<Product> products= productService.getProductByCategoryIdOrderByView(id, pageable);
            List<AdminProductView> adminProductViews = getAdminProductViews(id,products);

            return new ResponseEntity<>(adminProductViews, HttpStatus.OK);

        }

        @GetMapping(value="/getProductByBestSelling/{id}/{page}")
        public ResponseEntity<?> getProductBySale(@PathVariable("id") long id,@PathVariable("page") long page){

            Pageable pageable = PageRequest.of((int) page, 20);

            List<Product> products= productService.getProductByCategoryIdOrderBySale(id, pageable);
            List<AdminProductView> adminProductViews = getAdminProductViews(id,products);

            return new ResponseEntity<>(adminProductViews, HttpStatus.OK);

        }

        @GetMapping(value="/getProductByPriceAsc/{id}/{page}")
        public ResponseEntity<?> getProductByPriceAsc(@PathVariable("id") long id,@PathVariable("page") long page){

            Pageable pageable = PageRequest.of((int) page, 20);

            List<Product>products= productService.getProductByCategoryIdOrderByPriceAsc(id, pageable);

            List<AdminProductView> adminProductViews = getAdminProductViews(id,products);

            return new ResponseEntity<>(adminProductViews, HttpStatus.OK);

        }

        @GetMapping(value="/getProductByPriceDesc/{id}/{page}")
        public ResponseEntity<?> getProductByPriceDesc(@PathVariable("id") long id,@PathVariable("page") long page){

            Pageable pageable = PageRequest.of((int) page, 20);

            List<Product> products= productService.getProductByCategoryIdOrderByPriceDesc(id, pageable);


            List<AdminProductView> adminProductViews = getAdminProductViews(id,products);

            return new ResponseEntity<>(adminProductViews, HttpStatus.OK);

        }

        private List<AdminProductView> getAdminProductViews(long id, List<Product> products) {
            List<AdminProductView> result = new ArrayList<>();
            long size = productRepository.countByCategoryId(id);
            if(id==-1) size=productRepository.count();
            ModelMapper mapper = new ModelMapper();
            for (Product p : products) {
                AdminProductView product = mapper.map(p, AdminProductView.class);
                product.setSize(size);
                result.add(product);
            }

            return result;

        }



    }




























