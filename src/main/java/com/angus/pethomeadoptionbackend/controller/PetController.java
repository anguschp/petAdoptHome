package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.service.PetService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;


@RestController
public class PetController {

   @Autowired
   private PetService petService;
   private static final Logger logger = LoggerFactory.getLogger(PetController.class);


   @GetMapping("/pet/petlist")
   public ResponseEntity<List<PetSearchResponse>> getPetList(
                                                              @RequestParam(required = false) Integer breed,
                                                              @RequestParam(required = false) Integer gender,
                                                              @RequestParam(required = false) Integer age,
                                                              @RequestParam(required = false) Integer limit,
                                                              @RequestParam(defaultValue = "1" , required = false) Integer category
                                                        )
   {


      PetSearchRequest petSearchRequest = new PetSearchRequest();
      if(breed != null){petSearchRequest.setBreed(breed);}
      if(gender != null){petSearchRequest.setGender(gender);}
      if(age != null){petSearchRequest.setAge(age);}
      if(limit != null){petSearchRequest.setLimit(limit);}
      if(category != null){petSearchRequest.setCategory(category);}

      logger.info("petSearchRequest" + petSearchRequest);


      List<PetSearchResponse> resultList = petService.getPetList(petSearchRequest);

      return ResponseEntity.status(HttpStatus.OK).body(resultList);


   }


}
