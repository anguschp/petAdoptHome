package com.angus.pethomeadoptionbackend.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PetThumbnail {

    private int petId;
    private String petName;
    private Date createdDate;
    private List<String> imageUrl;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
