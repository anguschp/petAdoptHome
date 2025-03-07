package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.model.User;

public interface UserService {

    public User getUserById(Integer id);
    public User getUserByEmail(String email);
    public User getUserByUsername(String username);
    public User createUser(User user);
    public User updateUserById(int id , User user);
    public void deleteUserById(int id);


}
