package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.ClientProductView;
import com.innerclan.v1.entity.Design;
import com.innerclan.v1.exception.DesignNotSentException;
import com.innerclan.v1.repository.DesignRepository;
import com.innerclan.v1.service.IDesignService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping(value="/api/v1/admin/design")
public class AdminDesignController {

    @Autowired
    IDesignService designService;


    @GetMapping(value="/getDesigns")
    public ResponseEntity<?> getDesigns(){

        List<Design> designs= designService.getAllDesigns();

        return new ResponseEntity<>(designs, HttpStatus.OK);

    }

    @GetMapping(value="/getUnseenDesigns")
    public ResponseEntity<?> getUnseenDesigns(){

        List<Design> designs= designService.getUnseenDesigns();

        return new ResponseEntity<>(designs, HttpStatus.OK);

    }

    @GetMapping(value="/getSeenDesigns")
    public ResponseEntity<?> getSeenDesigns(){

        List<Design> designs= designService.getSeenDesigns();

        return new ResponseEntity<>(designs, HttpStatus.OK);

    }

    @DeleteMapping(value="")
    public ResponseEntity<?> getSeenDesigns(@RequestParam("id") long id){

       designService.deleteDesign(id);

        return new ResponseEntity<>("DesignDeleted Successfully", HttpStatus.OK);

    }



}
