package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.model.User;
import com.angus.pethomeadoptionbackend.service.ApplicationService;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/{userId}/submitApplication")
    public ResponseEntity<?> submitApplication(@PathVariable @Min(0) Integer userId) {

        Integer applicantID = userId;

        logger.info("submitted adopt application from user: " + applicantID);


        applicationService.applyAdoption(applicantID);

        return ResponseEntity.status(HttpStatus.OK).body("Application submitted");
    }

}
