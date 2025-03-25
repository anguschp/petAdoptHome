package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetServiceImpl implements PetService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PetDao petDao;
    @Autowired
    private GalleryImageDao galleryImageDao;
    @Value("${SERVER_ADDRESS}")
    private String serverAddress;
    @Value("${SERVER_PORT}")
    private String serverPort;


    @Override
    public List<PetSearchResponse> getPetList(PetSearchRequest PetSearchRequest) {

        List<PetSearchResponse> resultList = new ArrayList<>();

        try{
            resultList = petDao.getPetProfileList(PetSearchRequest);

            //get Image URL for each pet item
            for(PetSearchResponse item : resultList) {

                List<String> urlString = getImageListByPet(item);
                item.setImageURL(urlString);

            }
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


        if(!resultList.isEmpty())
        {
            return resultList;
        }
        else return null;

    }

    @Override
    public PetSearchResponse getPetById(Integer petId) {

        PetSearchResponse returnPet = petDao.getPetById(petId);
        returnPet.setImageURL(getImageListByPet(returnPet));

        return returnPet;
    }


    private List<String> getImageListByPet(PetSearchResponse pet)
    {
        List<GalleryImage> relativeImages = galleryImageDao.getImagesByPetId(pet.getPet_id());

        List<String> urlString  = new ArrayList<>();

        if(!relativeImages.isEmpty())
        {
            for(GalleryImage image : relativeImages)
            {
                urlString.add("http://"+serverAddress + ":" + serverPort + "/images" + image.getImageURL());
            }
        }

        return urlString;
    }


}
