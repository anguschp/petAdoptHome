package com.angus.pethomeadoptionbackend.dao;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.model.Pet;

import java.util.List;

public interface PetDao {

    public List<Pet> getPetProfileList(PetSearchRequest PetSearchRequest);



}
