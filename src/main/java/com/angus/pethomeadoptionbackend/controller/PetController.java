package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.NewPetDTO;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@RestController
public class PetController {

   @Autowired
   private PetService petService;
   private static final Logger logger = LoggerFactory.getLogger(PetController.class);
   @Value("${SERVER_ADDRESS}")
   private String ipAddress;
   @Value("${SERVER_PORT}")
   private String serverPort;
   @Value("${IMG_FOLDER_URL}")
   private String imgFolderUrl;


   @GetMapping("/pet/petlist")
   public ResponseEntity<List<PetSearchResponse>> getPetList(
                                                              @RequestParam(required = false) Integer breed,
                                                              @RequestParam(required = false) Integer gender,
                                                              @RequestParam(required = false) Integer age,
                                                              @RequestParam(required = false) Integer limit,
                                                              @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String serialNo,
                                                              @RequestParam(defaultValue = "1" , required = false) Integer category
                                                        )
   {


      PetSearchRequest petSearchRequest = new PetSearchRequest();
      if(breed != null){petSearchRequest.setBreed(breed);}
      if(gender != null){petSearchRequest.setGender(gender);}
      if(age != null){petSearchRequest.setAge(age);}
      if(limit != null){petSearchRequest.setLimit(limit);}
      if(category != null){petSearchRequest.setCategory(category);}
      if(name != null){petSearchRequest.setName(name);}
      if(serialNo != null){petSearchRequest.setSerialNo(serialNo);}


      logger.info("petSearchRequest" + petSearchRequest);


      List<PetSearchResponse> resultList = petService.getPetList(petSearchRequest);

      return ResponseEntity.status(HttpStatus.OK).body(resultList);

   }

   @GetMapping("/pet/{petId}")
   public ResponseEntity<PetSearchResponse>searchPetById(@PathVariable Integer petId)
   {
      PetSearchResponse petResult = petService.getPetById(petId);

      if(petResult != null){
         return ResponseEntity.status(HttpStatus.OK).body(petResult);
      }else
         return null;

   }

   @PostMapping(value = "/pet/createNewPet" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> createNewPet(@RequestPart("petData") String petDataJSON, @RequestPart("imagefiles") MultipartFile[] imageFiles)
   {

      try {
         // Convert JSON string to Java object
         ObjectMapper objectMapper = new ObjectMapper();
         NewPetDTO petData = objectMapper.readValue(petDataJSON, NewPetDTO.class);

         petService.createNewPet(petData , imageFiles);

         if(petData.isNewBreedIndicator())
         {
            if(petData.getPetNewBreed() == null || petData.getPetNewBreed().isBlank())
            {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New breed is blank");
            }

         }
      return ResponseEntity.status(HttpStatus.CREATED).body("ok");
   }catch (Exception err)
      {
         System.out.println(err);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
      }
   }


//   private String saveImage(MultipartFile image) throws IOException {
//      String uploadDir = imgFolderUrl;
//      String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
//      Path uploadPath = Paths.get(uploadDir);
//
//      if (!Files.exists(uploadPath)) {
//         Files.createDirectories(uploadPath);
//      }
//
//      try (InputStream inputStream = image.getInputStream()) {
//         Path filePath = uploadPath.resolve(fileName);
//         Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//         return filePath.toString();
//      }
//   }



}
