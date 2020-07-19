package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.dto.ClientProductView;
import com.innerclan.v1.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IProductService {


    List<ClientProductView> getProductByCategoryId(long id, Pageable pageable);

    List<ClientProductView> getProductByCategoryIdOrderByView(long id, Pageable pageable);

    List<ClientProductView> getProductByCategoryIdOrderBySale(long id, Pageable pageable);

    List<ClientProductView> getProductByCategoryIdOrderByPriceAsc(long id, Pageable pageable);

    List<ClientProductView> getProductByCategoryIdOrderByPriceDesc(long id, Pageable pageable);
}
