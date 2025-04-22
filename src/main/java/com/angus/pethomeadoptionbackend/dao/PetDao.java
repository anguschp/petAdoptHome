package com.angus.pethomeadoptionbackend.dao;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.NewPetDTO;
import com.angus.pethomeadoptionbackend.model.Pet;

import java.util.List;

public interface PetDao {

    public List<PetSearchResponse> getPetProfileList(PetSearchRequest PetSearchRequest);

    public PetSearchResponse getAvailablePetById(Integer id);

    public PetSearchResponse getPetById(Integer id);

    public void updatePetAvailable(Integer id, Integer available);

    public Integer addPet(NewPetDTO pet);
}
