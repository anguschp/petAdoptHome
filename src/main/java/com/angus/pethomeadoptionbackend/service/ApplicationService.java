package com.angus.pethomeadoptionbackend.service;


import com.angus.pethomeadoptionbackend.model.Application;

import java.util.List;

public interface ApplicationService {

    public void applyAdoption(Integer applicantId);

    public List getUserApplication(Integer userd, Integer sortBy, Integer orderSeq);

    public List getApplcationsByAdmin(Integer status , Integer orderSeq);

    public Application getApplicationById(Integer appId);

    public void updateApplication(Integer appId , Integer userId, Integer status);
}
