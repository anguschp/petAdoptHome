package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.NewPetDTO;
import com.angus.pethomeadoptionbackend.model.Pet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface PetService {

    public List<PetSearchResponse> getPetList(PetSearchRequest PetSearchRequest);

    public PetSearchResponse getAvailablePetById(Integer petId);

    public PetSearchResponse getPetById(Integer petId);

    public void createNewPet(NewPetDTO newPetData, MultipartFile[] imageFiles);
}
