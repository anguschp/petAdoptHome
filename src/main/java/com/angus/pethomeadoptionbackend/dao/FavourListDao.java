package com.angus.pethomeadoptionbackend.dao;

import com.angus.pethomeadoptionbackend.dto.FavourList;
import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;
import com.angus.pethomeadoptionbackend.model.Pet;

import java.util.List;

public interface FavourListDao {

    public void addPetToFavourList(Integer userId, Integer petId);

    public void removePetFromFavourList(Integer userId , Integer petId);

    public FavourList getUserFavourListById(Integer userId);

    public Integer petExistInFavourList(Integer userId, Integer petId);

    public List<FavourListWithPet> getUserFavourPetList(Integer userId);

}
