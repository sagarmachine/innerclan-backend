package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Color;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashSet;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/admin/product")
@CrossOrigin(value = "http://localhost:3001")
public class AdminProductController {


    @Autowired
    IBindingErrorService bindingErrorService;

    @Autowired
    IProductService productService;


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

    @PostMapping(value = "/addColors/{id}")
    public ResponseEntity<Set<Color>>  addColors(@PathVariable("id") long productId, @RequestBody List<String> colorList){
        HashSet<String> colors= new HashSet<>(colorList);
       return  ResponseEntity.ok().body(productService.addColors(productId,colors));
    }


    @PostMapping(value="addColorImage/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Color> addImage( @PathVariable("id")long colorId,@RequestBody  MultipartFile file){

    return   new ResponseEntity<>(productService.addImage(colorId,file),HttpStatus.ACCEPTED);

}


}
