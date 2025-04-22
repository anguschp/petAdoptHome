package com.angus.pethomeadoptionbackend.repo;

import com.angus.pethomeadoptionbackend.model.Breed;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BreedRepo extends CrudRepository<Breed, Integer> {

    public List<Breed> findByBreedName(String breedName);

}
