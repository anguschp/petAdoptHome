package com.angus.pethomeadoptionbackend.repo;

import com.angus.pethomeadoptionbackend.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {

    public User findByEmail(String email);

    public User findUserByUsername (String username);

}
