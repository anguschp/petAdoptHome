package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.configuration.GeneralExceptionHandler;
import com.angus.pethomeadoptionbackend.dto.RegisterRequest;
import com.angus.pethomeadoptionbackend.dto.UserLoginRequest;
import com.angus.pethomeadoptionbackend.model.User;
import com.angus.pethomeadoptionbackend.repo.UserRepo;
import com.angus.pethomeadoptionbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.angus.pethomeadoptionbackend.customException.ResourceAlreadyExistException;
import org.springframework.web.server.ResponseStatusException;



import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl() {
        super();
    }

    @Override
    public User getUserById(Integer id){
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User createUser(RegisterRequest registerRequest) {

        User usernameInUse = userRepo.findUserByUsername(registerRequest.getUsername());
        User emailInUse = userRepo.findByEmail(registerRequest.getEmail());

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername().trim());
        newUser.setEmail(registerRequest.getEmail().trim());
        newUser.setPassword(registerRequest.getPassword().trim());
        newUser.setCreated_date(new Date());
        newUser.setLast_modified_date(new Date());
        newUser.setRole("ROLE_USER");

        HashMap<String ,String> exceptionMap = new HashMap<>();

        if(usernameInUse != null) {
            exceptionMap.put("username" , "already in use");
        }
        if(emailInUse != null) {
            exceptionMap.put("email" , "already in use");
        }
        if(!exceptionMap.isEmpty())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            throw new ResourceAlreadyExistException(exceptionMap);
        }
        else
        {
            newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            return userRepo.save(newUser);
        }

    }

    @Override
    public User updateUserById(int id , User user) {
        user.setUserid(id);

        User tempUser = userRepo.findById(id).orElse(null);
        if(tempUser != null) {
            tempUser.setPassword(user.getPassword());
            return userRepo.save(user);
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "User not found, update failed");
    }

    @Override
    public User getUserByUsername(String username) {

        User user = userRepo.findUserByUsername(username);

        if(user != null)
        {
            return user;
        }
        else {
            throw new UsernameNotFoundException("User does not exist");
        }
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public boolean authenticateUser(UserLoginRequest userLoginRequest) {
        User user = userRepo.findUserByUsername(userLoginRequest.getUsername());
        boolean result = false;
        if(user != null)
        {
            if(passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            {
                result = true;
            }
        }else result =  false;

        return result;
    }


}
