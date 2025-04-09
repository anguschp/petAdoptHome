package com.angus.pethomeadoptionbackend.dao;

import com.angus.pethomeadoptionbackend.model.Application;
import com.angus.pethomeadoptionbackend.model.ApplicationRecord;

import java.util.List;

public interface ApplicationDao {

    public void submitApplication(Integer userId);

    public List<ApplicationRecord> getApplicationsByUserId(Integer userId, Integer sortBy , Integer orderSeq);

    public List<ApplicationRecord> getApplicationByAdmin(Integer status , Integer orderSeq);

    public Application getApplicationById(Integer Id);

    public void updateApplication(Integer appId , Integer userId, Integer status);
}
