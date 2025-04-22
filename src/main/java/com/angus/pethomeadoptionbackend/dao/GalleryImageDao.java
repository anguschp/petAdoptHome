package com.angus.pethomeadoptionbackend.dao;

import com.angus.pethomeadoptionbackend.model.GalleryImage;

import java.util.List;

public interface GalleryImageDao {

    public List<GalleryImage> getImagesByPetId(Integer petId);

    public void save(GalleryImage galleryImage);
}
