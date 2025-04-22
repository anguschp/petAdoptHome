package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.Breed;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.model.NewPetDTO;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.repo.BreedRepo;
import com.angus.pethomeadoptionbackend.service.GalleryImageService;
import com.angus.pethomeadoptionbackend.service.PetService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PetServiceImpl implements PetService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PetDao petDao;
    @Autowired
    private GalleryImageDao galleryImageDao;
    @Autowired
    private GalleryImageService galleryImageService;
    @Autowired
    private BreedRepo breedRepo;

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

    @Override
    public PetSearchResponse getAvailablePetById(Integer petId) {

        PetSearchResponse returnPet = petDao.getAvailablePetById(petId);
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


    @Transactional
    @Override
    public void createNewPet(NewPetDTO newPetData , MultipartFile[] imageFiles) {

        Integer proceedBreed = processBreed(newPetData);
        if(proceedBreed != null)
        {
            newPetData.setPetBreed(proceedBreed);
        }

        //save pet in petList
        //get back the newly pet Id for inserting new image in Gallery
        Integer newPetID = petDao.addPet(newPetData);

        if(newPetID != null)
        {
            try
            {
                galleryImageService.saveImage(imageFiles , newPetID);
            }catch(Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }

    }


    private Integer processBreed(NewPetDTO newPetData)
    {
        if(!newPetData.isNewBreedIndicator())
        {
            return newPetData.getPetBreed();
        }

        List result = breedRepo.findByBreedName(newPetData.getPetNewBreed());
        if(!result.isEmpty())
        {
            throw new RuntimeException("Breed already exists");
        }

        Breed breed = new Breed();
        breed.setBreedName(newPetData.getPetNewBreed());
        breed.setCreated_date(new Date());
        breed.setLast_modified_date(new Date());
        Breed returnBreed =  breedRepo.save(breed);
        return returnBreed.getBreedId();
    }



}
