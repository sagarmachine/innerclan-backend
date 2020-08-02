package com.innerclan.v1.controller.client;



import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.innerclan.v1.entity.Design;
import com.innerclan.v1.exception.DesignNotSentException;
import com.innerclan.v1.exception.ProductNotSavedException;
import com.innerclan.v1.repository.DesignRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> sendDesign(@RequestParam("email") String email,
                                        @RequestParam("comment") String comment,
                                        @RequestBody MultipartFile file ){

        Design design = new Design(email,comment);
        try {
            design.setImage(file.getBytes());
        } catch (IOException ex) {
            throw new DesignNotSentException("Try Different Image or Different Image Format");
        }
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
