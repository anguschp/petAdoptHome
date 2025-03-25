package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.dto.FavourList;
import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;

import java.util.List;

public interface FavourListService
{

    public List<FavourListWithPet> getUserFavourPetList(Integer userId);

    public FavourList getFavourListById(Integer userId);

    public void addPetToList(Integer userId, Integer petId);

    public void removePetFromList(Integer userId, Integer petId);
}
