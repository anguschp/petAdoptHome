package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.UtilListDao;
import com.angus.pethomeadoptionbackend.dto.UtilResponse;
import com.angus.pethomeadoptionbackend.model.Breed;
import com.angus.pethomeadoptionbackend.model.Gender;
import com.angus.pethomeadoptionbackend.service.UtilListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UtilServiceImpl implements UtilListService {

    @Autowired
    private UtilListDao utilListDao;

    private UtilResponse utilResponse = new UtilResponse();

    @Override
    public UtilResponse getUtilList() {

        List<Breed> tempBreedList = utilListDao.getBreedDetail();

        List<Gender> tempGenderList = utilListDao.getGenderDetail();

        if(tempGenderList != null)
        {
            utilResponse.setGenderList(tempGenderList);
        }

        if(tempBreedList != null)
        {
            utilResponse.setBreedList(tempBreedList);
        }

        return utilResponse;
    }
}
