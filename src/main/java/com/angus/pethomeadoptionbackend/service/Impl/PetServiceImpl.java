package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetServiceImpl implements PetService {

    @Autowired
    private PetDao petDao;

    @Autowired
    private GalleryImageDao galleryImageDao;

    @Override
    public List<PetSearchResponse> getPetList(PetSearchRequest PetSearchRequest) {

        List<PetSearchResponse> resultList = petDao.getPetProfileList(PetSearchRequest);

        for(PetSearchResponse item : resultList){

            List<GalleryImage> relativeImages = galleryImageDao.getImagesByPetId(item.getPet_id());

            if(relativeImages != null && relativeImages.size() > 0)
            {
                List<String> urlString  = new ArrayList<>();
                for(GalleryImage image : relativeImages)
                {
                    urlString.add(image.getImageURL());
                }

                item.setImageURL(urlString);
            }

        }


        if(resultList.size() > 0)
        {
            return resultList;
        }
        else return null;

    }




}
