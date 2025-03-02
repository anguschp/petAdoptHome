package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetServiceImpl implements PetService {

    @Autowired
    private PetDao petDao;

    @Override
    public List<Pet> getPetList(PetSearchRequest PetSearchRequest) {
        return petDao.getPetProfileList(PetSearchRequest);
    }




}
