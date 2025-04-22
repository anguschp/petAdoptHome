package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.service.GalleryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class GalleryImageServiceImpl implements GalleryImageService {

    @Autowired
    private GalleryImageDao galleryImageDao;

    @Override
    public List<GalleryImage> getImagesByPetId(Integer petId) {
        return galleryImageDao.getImagesByPetId(petId);
    }

    @Value("${IMG_FOLDER_URL}")
    private String imgFolderUrl;


    @Override
    public void saveImage(MultipartFile[] imageFiles , Integer petId) throws IOException{

        for(MultipartFile image : imageFiles){

            GalleryImage tempImage = new GalleryImage();
            tempImage.setPetId(petId);
            String generatedFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            tempImage.setImageURL("/" + generatedFileName);
            tempImage.setCreated_date(new Date());
            tempImage.setLast_modified_date(new Date());

            galleryImageDao.save(tempImage);
            saveImageInFolder(image , generatedFileName);

        }


    }

    private void saveImageInFolder(MultipartFile image , String fileName) throws IOException
    {

        String uploadDir = imgFolderUrl;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

    }


}
