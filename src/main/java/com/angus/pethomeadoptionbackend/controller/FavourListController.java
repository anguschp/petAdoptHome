package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.FavourList;
import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;
import com.angus.pethomeadoptionbackend.dto.FavourPetRequest;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.service.FavourListService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavourListController {

    @Autowired
    private FavourListService favourListService;


    @GetMapping("/{userId}/favoutList/getList")
    public ResponseEntity<?> getFavoutListById(@PathVariable @NotNull @Min(0) Integer userId)
    {
        FavourList result = favourListService.getFavourListById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/{userId}/favourList/fullList")
    public ResponseEntity<?> getFavourPetListById(@PathVariable @NotNull @Min(0) Integer userId)
    {
        List<FavourListWithPet> resultList = favourListService.getUserFavourPetList(userId);

        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }



    @PostMapping("/{userId}/favourList/addPet")
    public ResponseEntity<?> addPetToList(@PathVariable @NotNull Integer userId, @RequestBody FavourPetRequest request)
    {
        favourListService.addPetToList(userId , request.getPetId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Added to favour list successfully");
    }


    @PostMapping("/{userId}/favourList/removePet")
    public ResponseEntity<?> removePetFromList(@PathVariable @NotNull Integer userId, @RequestBody FavourPetRequest request)
    {
        favourListService.removePetFromList(userId , request.getPetId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Removed successfully");
    }


}
