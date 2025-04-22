package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.model.GalleryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GalleryImageService {

    public List<GalleryImage> getImagesByPetId(Integer petId);

    public void saveImage(MultipartFile[] imageFiles , Integer petId) throws IOException;
}