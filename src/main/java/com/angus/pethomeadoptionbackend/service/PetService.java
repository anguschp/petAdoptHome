package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.model.Pet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PetService {

    public List<Pet> getPetList(PetSearchRequest PetSearchRequest);


}
