package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.service.GalleryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GalleryImageServiceImpl implements GalleryImageService {

    @Autowired
    private GalleryImageDao galleryImageDao;

    @Override
    public List<GalleryImage> getImagesByPetId(Integer petId) {
        return galleryImageDao.getImagesByPetId(petId);
    }
}
