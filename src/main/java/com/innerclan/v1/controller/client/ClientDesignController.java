package com.innerclan.v1.controller.client;



import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.innerclan.v1.entity.Design;
import com.innerclan.v1.exception.DesignNotSentException;
import com.innerclan.v1.exception.ImageNotSavedException;
import com.innerclan.v1.exception.ProductNotSavedException;
import com.innerclan.v1.repository.DesignRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping(value="/api/v1/design")
public class ClientDesignController {

    @Autowired
    DesignRepository designRepo;

    String url="https://api.imgbb.com/1/upload?key=dcbdc94a138d3a04d52f008ec67168a5";


    @PostMapping(value = "",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> sendDesign(@RequestParam("email") String email,
                                        @RequestParam("comment") String comment,
                                        @RequestBody MultipartFile file ){

        Design design = new Design(email,comment);

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
        design.setImage((String)((HashMap)result.get("data")).get("display_url"));
        design.setDeleteImage((String)result.get("delete_url"));
       designRepo.save(design);
        return new ResponseEntity<>("SUBMITTED", HttpStatus.ACCEPTED);


    }
   //--- compressing algorithm
/*
    public static byte[] compressBytes(byte[] data) {

        Deflater deflater = new Deflater();

        deflater.setInput(data);

        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {

            int count = deflater.deflate(buffer);

            outputStream.write(buffer, 0, count);

        }

        try {

            outputStream.close();

        } catch (IOException e) {

        }

        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();

    }

 */
}
