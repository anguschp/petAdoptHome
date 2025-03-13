package com.angus.pethomeadoptionbackend.service;

import com.angus.pethomeadoptionbackend.dto.RegisterRequest;
import com.angus.pethomeadoptionbackend.dto.UserLoginRequest;
import com.angus.pethomeadoptionbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService{

    public User getUserById(Integer id);
    public User getUserByEmail(String email);
    public User getUserByUsername(String username);
    public User createUser(RegisterRequest registerRequest);
    public User updateUserById(int id , User user);
    public void deleteUserById(int id);
    public boolean authenticateUser(UserLoginRequest userLoginRequest);


}
