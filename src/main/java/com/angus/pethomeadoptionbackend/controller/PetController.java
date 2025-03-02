package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.constant.Gender;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.angus.pethomeadoptionbackend.model.PetThumbnail;

import java.util.List;

@RestController
public class PetController {

   @Autowired
   private PetService petService;

   @GetMapping
   public ResponseEntity<List<PetThumbnail>> getPetList(@RequestParam Integer pet_id,
                                                        @RequestParam String serial_no,
                                                        @RequestParam Integer breed,
                                                        @RequestParam Gender gender,
                                                        @RequestParam int age,
                                                        @RequestParam int limit
                                                        )
   {

      PetSearchRequest petSearchRequest = new PetSearchRequest();
      petSearchRequest.setPet_id(pet_id);
      petSearchRequest.setSerial_no(serial_no);
      petSearchRequest.setBreed(breed);
      petSearchRequest.setGender(gender);
      petSearchRequest.setAge(age);
      petSearchRequest.setLimit(limit);

      List<Pet> resultList = petService.getPetList(petSearchRequest);

   }


}
