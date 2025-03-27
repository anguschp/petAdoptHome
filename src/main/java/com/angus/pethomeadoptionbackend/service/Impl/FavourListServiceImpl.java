package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.FavourListDao;
import com.angus.pethomeadoptionbackend.dto.FavourList;
import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;
import com.angus.pethomeadoptionbackend.service.FavourListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavourListServiceImpl implements FavourListService {

    @Autowired
    private FavourListDao favourListDao;


    @Override
    public List<FavourListWithPet> getUserFavourPetList(Integer userId) {

        List<FavourListWithPet> petList = favourListDao.getUserFavourPetList(userId);

        if(petList != null){
            return petList;
        }else
            return null;


    }

    @Override
    public FavourList getFavourListById(Integer userId) {

        FavourList result = favourListDao.getUserFavourListById(userId);

        if(result != null) {
            System.out.println(result);
            return result;
        }else
            return null;

    }

    @Override
    public void addPetToList(Integer userId, Integer petId) {
        favourListDao.addPetToFavourList(userId, petId);
    }

    @Override
    public void removePetFromList(Integer userId, Integer petId) {

        Integer recordExistInList = favourListDao.petExistInFavourList(userId, petId);
        if(recordExistInList < 1)
        {
            throw new RuntimeException("No record exist in favout list");
        }

        favourListDao.removePetFromFavourList(userId, petId);
    }
}
