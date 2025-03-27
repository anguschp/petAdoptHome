package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.ApplicationDao;
import com.angus.pethomeadoptionbackend.dao.FavourListDao;
import com.angus.pethomeadoptionbackend.service.ApplicationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private FavourListDao favourListDao;

    @Transactional
    @Override
    public void applyAdoption(Integer applicantId) {

        applicationDao.submitApplication(applicantId);
        favourListDao.clearAllPetFromFavourList(applicantId);

    }
}
