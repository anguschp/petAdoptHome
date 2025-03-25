package com.angus.pethomeadoptionbackend.dto;

import com.angus.pethomeadoptionbackend.model.Breed;
import com.angus.pethomeadoptionbackend.model.Gender;

import java.util.List;

public class UtilResponse {

    private List<Breed> breedList;
    private List<Gender> genderList;

    public UtilResponse() {
    }

    public List<Breed> getBreedList() {
        return breedList;
    }

    public void setBreedList(List<Breed> breedList) {
        this.breedList = breedList;
    }

    public List<Gender> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<Gender> genderList) {
        this.genderList = genderList;
    }
}
