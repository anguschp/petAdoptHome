package com.angus.pethomeadoptionbackend.dto;

import java.util.Date;

public class FavourListWithPet {

    private Integer id;
    private Integer favourListId;
    private Integer pet_id;
    private Date favourCreateDate;
    private String petName;
    private Integer age;
    private String gender;
    private String breed;
    private Integer petStatus;
    private String imageSingleURL;

    public FavourListWithPet() {
    }

    public String getImageSingleURL() {
        return imageSingleURL;
    }

    public void setImageSingleURL(String imageSingleURL) {
        this.imageSingleURL = imageSingleURL;
    }

    public Integer getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(Integer petStatus) {
        this.petStatus = petStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFavourListId() {
        return favourListId;
    }

    public void setFavourListId(Integer favourListId) {
        this.favourListId = favourListId;
    }

    public Integer getPet_id() {
        return pet_id;
    }

    public void setPet_id(Integer pet_id) {
        this.pet_id = pet_id;
    }

    public Date getFavourCreateDate() {
        return favourCreateDate;
    }

    public void setFavourCreateDate(Date favourCreateDate) {
        this.favourCreateDate = favourCreateDate;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
