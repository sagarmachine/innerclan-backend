package com.innerclan.v1.controller.admin;

import com.innerclan.v1.dto.ShowCase;
import com.innerclan.v1.entity.UIElements;
import com.innerclan.v1.exception.ImageNotSavedException;
import com.innerclan.v1.repository.ShowcaseRepository;
import com.innerclan.v1.repository.UIElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/v1/admin/ui")
public class AdminUIController {


    @Autowired
    UIElementsRepository uiElementsRepository;

    @Autowired
    ShowcaseRepository showcaseRepository;

    @Value("${imgbb.url}")
    String url;


    @PostMapping(value="")
    public ResponseEntity<?> add(@RequestBody UIElements uiElements){
        uiElementsRepository.save(uiElements);
        return new ResponseEntity<>("UI added successfully ", HttpStatus.OK);
    }

    @GetMapping(value="")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>(uiElementsRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UIElements uiElements){
        uiElementsRepository.deleteAll();
        uiElementsRepository.save(uiElements);
        return new ResponseEntity<>("UI updated successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/showcase",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void addImageToShowcase(@RequestBody MultipartFile[] files){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        System.out.println(files.length+" - files");

        for(MultipartFile file:files){
            ShowCase showCase= new ShowCase();
            try {
                map.add("image", Base64.getEncoder().encodeToString(file.getBytes()));

            } catch (Exception ex) {
                System.out.println("image was not saved try a different image");
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);


            HashMap<String, Object> result = restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class).getBody();
            //  System.out.println((String)((HashMap)result.get("data")).get("display_url"));
            showCase.setImage((String) ((HashMap) result.get("data")).get("display_url"));
            showCase.setDeleteImage((String)result.get("delete_url"));
            showcaseRepository.save(showCase);
        }
    }

    @DeleteMapping(value = "/showcase/{id}")
    public  void getShowcase(@PathVariable Long id){
        showcaseRepository.deleteById(id);
    }
}
