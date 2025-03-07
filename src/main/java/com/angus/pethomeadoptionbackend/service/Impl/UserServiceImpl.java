package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.model.User;
import com.angus.pethomeadoptionbackend.repo.UserRepo;
import com.angus.pethomeadoptionbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    private UserRepo repo;
    private PasswordEncoder encoder;

    private UserRepo userRepo;


    public UserServiceImpl() {
        super();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User updateUserById(int id , User user) {
        user.setUserid(id);

        User tempUser = userRepo.findById(id).orElse(null);
        if(tempUser != null) {
            tempUser.setPassword(encoder.encode(user.getPassword()));
            return userRepo.save(user);
        }else
            throw new RuntimeException("User not found, update failed");
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUserById(int id) {

    }
}
