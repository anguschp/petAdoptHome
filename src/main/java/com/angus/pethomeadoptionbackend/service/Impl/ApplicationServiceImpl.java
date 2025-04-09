package com.angus.pethomeadoptionbackend.service.Impl;

import com.angus.pethomeadoptionbackend.dao.ApplicationDao;
import com.angus.pethomeadoptionbackend.dao.FavourListDao;
import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.Application;
import com.angus.pethomeadoptionbackend.model.ApplicationRecord;
import com.angus.pethomeadoptionbackend.service.ApplicationService;
import com.angus.pethomeadoptionbackend.service.PetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private FavourListDao favourListDao;

    @Autowired
    EmailService emailService;
    @Autowired
    private PetDao petDao;


    @Transactional
    @Override
    public void applyAdoption(Integer applicantId) {

        applicationDao.submitApplication(applicantId);
        favourListDao.clearAllPetFromFavourList(applicantId);

    }


    @Override
    public List<ApplicationRecord> getUserApplication(Integer userd, Integer sortBy, Integer orderSeq) {
        return applicationDao.getApplicationsByUserId(userd, sortBy, orderSeq);
    }


    @Override
    public List getApplcationsByAdmin(Integer status, Integer orderSeq) {
        return applicationDao.getApplicationByAdmin(status , orderSeq);
    }


    @Override
    public Application getApplicationById(Integer appId) {

        if(appId != null) {
            return applicationDao.getApplicationById(appId);
        }else{
            return null;
        }

    }

    @Transactional
    @Override
    public void updateApplication(Integer appId, Integer userId, Integer status) {

        if(status == 1) { //approve


            Application application = applicationDao.getApplicationById(appId);

            //get back pet info
            PetSearchResponse pet = petDao.getAvailablePetById(application.getPet_id());

            applicationDao.updateApplication(appId, userId, status);
            petDao.updatePetAvailable(application.getPet_id() , 0);


            String subject = "Your application has been approved";
            String body = "Hello User,\n\n" + "Your application has been approved.\n\n" +
                    "Application Number: " + application.getApply_no() + "\n\n" +
                    "Pet: " + pet.getName() + "\n\n" +
                    "Pet serial no.: " + pet.getSerial_number() ;


            emailService.sendPlainText(null , subject , body);
        }
        if(status == 3) {
            applicationDao.updateApplication(appId, userId, status);
        }

    }



}
