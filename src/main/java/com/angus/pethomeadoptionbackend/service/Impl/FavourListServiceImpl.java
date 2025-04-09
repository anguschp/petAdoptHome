package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.FavourListDao;
import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.dto.FavourList;
import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.service.FavourListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavourListServiceImpl implements FavourListService {

    @Autowired
    private FavourListDao favourListDao;

    @Autowired
    private GalleryImageDao galleryImageDao;

    @Value("${SERVER_ADDRESS}")
    private String serverAddress;
    @Value("${SERVER_PORT}")
    private String serverPort;


    @Override
    public List<FavourListWithPet> getUserFavourPetList(Integer userId) {

        List<FavourListWithPet> petList = favourListDao.getUserFavourPetList(userId);

        if(petList == null || petList.isEmpty())
        {
            return null;
        }else{

            for (FavourListWithPet petRecord : petList) {

                List<GalleryImage> result = galleryImageDao.getImagesByPetId((petRecord.getPet_id()));

                if (result.size() > 0) {

                    String finalURL = "http://"+serverAddress + ":" + serverPort + "/images" + result.get(0).getImageURL();
                    petRecord.setImageSingleURL(finalURL);
                }
            }

            return petList;

        }

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
