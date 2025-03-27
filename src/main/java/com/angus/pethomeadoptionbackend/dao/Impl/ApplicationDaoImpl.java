package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.ApplicationDao;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ApplicationDaoImpl implements ApplicationDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void submitApplication(Integer userId) {

        String executeStatement = "{call applyAdoption(:userId)}";
        HashMap parameters = new HashMap();

        parameters.put("userId", userId);

        try{
            namedParameterJdbcTemplate.update(executeStatement, parameters);
        }
        catch (Exception e){
            System.out.println("Stored procedure error: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        //call stored procedure

    }
}
