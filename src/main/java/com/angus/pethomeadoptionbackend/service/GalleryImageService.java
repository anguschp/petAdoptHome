package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.model.GalleryImage;

import java.util.List;

public interface GalleryImageService {

    public List<GalleryImage> getImagesByPetId(Integer petId);

}
