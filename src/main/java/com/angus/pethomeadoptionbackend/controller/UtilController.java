package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.UtilResponse;
import com.angus.pethomeadoptionbackend.service.UtilListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {

    @Autowired
    private UtilListService utilListService;

    @GetMapping("/util/getList")
    public ResponseEntity<?> retrieveUtil() {

        UtilResponse result = utilListService.getUtilList();

        return ResponseEntity.status(200).body(result);
    }

}
