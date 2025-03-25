package com.angus.pethomeadoptionbackend.dao;

import com.angus.pethomeadoptionbackend.model.Breed;
import com.angus.pethomeadoptionbackend.model.Gender;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UtilListDao {


    public List<Gender> getGenderDetail();

    public List<Breed> getBreedDetail();
}
