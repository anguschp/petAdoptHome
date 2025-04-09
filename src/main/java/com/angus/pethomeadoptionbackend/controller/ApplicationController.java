package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.ApplicationRequest;
import com.angus.pethomeadoptionbackend.dto.ApplicationSort;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.Application;
import com.angus.pethomeadoptionbackend.model.ApplicationDetail;
import com.angus.pethomeadoptionbackend.model.ApplicationRecord;
import com.angus.pethomeadoptionbackend.model.User;
import com.angus.pethomeadoptionbackend.service.ApplicationService;
import com.angus.pethomeadoptionbackend.service.PetService;
import com.angus.pethomeadoptionbackend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @PostMapping("/{userId}/submitApplication")
    public ResponseEntity<?> submitApplication(@PathVariable @Min(0) Integer userId) {

        Integer applicantID = userId;

        logger.info("submitted adopt application from user: " + applicantID);


        applicationService.applyAdoption(applicantID);

        return ResponseEntity.status(HttpStatus.OK).body("Application submitted");
    }

    @GetMapping("/{userId}/fetchApplications")
    public ResponseEntity<?> fetchApplications(@PathVariable @Valid @Min(1) Integer userId,
                                               @RequestParam(defaultValue = "1") @Valid @Min(1) @Max(2) Integer sortBy,
                                               @RequestParam @Valid @Min(-1) @Max(1) Integer orderSeq)
    {

        List<ApplicationRecord> result = applicationService.getUserApplication(userId, sortBy, orderSeq);
        if(result == null || result.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No applications found for user: " + userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/admin/applicationsRecord")
    public ResponseEntity<?> fetchApplicationsRecordByAdmin(@RequestParam(required = false) @Valid @Min(1) @Max(3) Integer status,
                                                            @RequestParam(defaultValue = "1") @Valid Integer orderSeq)
    {
        List<ApplicationRecord> result = applicationService.getApplcationsByAdmin(status , orderSeq);

        if(result == null || result.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/admin/ApplicationDetails/{appId}")
    public ResponseEntity<?> getApplicationDetail(
            @PathVariable(required = true) @Valid Integer appId) {

        Application record = applicationService.getApplicationById(appId);

        if (record == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ApplicationDetail result = new ApplicationDetail();

        PetSearchResponse petDetail = petService.getPetById(record.getPet_id());
        User userDetail = userService.getUserById(record.getApplicant_id());



        if (petDetail == null || userDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet or user not found");
        }

        result.setPet(petDetail);
        result.setUser(userDetail);
        result.setStatus(record.getApplyStatus());


        return ResponseEntity.ok(result);
    }


    @PutMapping("/admin/ApplicationDetails/action/{appId}")
    public ResponseEntity<String> updateApplicationDetail(@RequestBody(required = true) @Valid ApplicationRequest request,
                                                     @PathVariable(required = true) @Valid Integer appId)
    {

        User user = userService.getUserById(request.getUserId());
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for approve or reject");
        }

        applicationService.updateApplication(appId, request.getUserId(), request.getStatus());

        if(request.getStatus() == 1){
            return ResponseEntity.status(HttpStatus.OK).body("The application has been approved");
        }
        if(request.getStatus() == 3){
            return ResponseEntity.status(HttpStatus.OK).body("The application has been rejected");
        }

        return ResponseEntity.status(HttpStatus.OK).body("The application has been updated");
    }


}
