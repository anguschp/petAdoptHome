package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

   @Autowired
   private PetService petService;

   @GetMapping("/petlist")
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


      List<PetSearchResponse> resultList = petService.getPetList(petSearchRequest);

      return ResponseEntity.status(HttpStatus.OK).body(resultList);


   }


}
