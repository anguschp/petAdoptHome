package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.repo.UserRepo;
import com.angus.pethomeadoptionbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImpl implements AuthService {

   @Autowired
    private UserRepo userRepo;

}
