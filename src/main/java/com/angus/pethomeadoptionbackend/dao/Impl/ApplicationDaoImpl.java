package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.ApplicationDao;
import com.angus.pethomeadoptionbackend.model.Application;
import com.angus.pethomeadoptionbackend.model.ApplicationRecord;
import com.angus.pethomeadoptionbackend.rowMapper.ApplicationRecordRowMapper;
import com.angus.pethomeadoptionbackend.rowMapper.ApplicationRowMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Override
    public List<ApplicationRecord> getApplicationsByUserId(Integer userId, Integer sortBy, Integer orderSeq) {

        String sql = "SELECT\n" +
                "\taa.application_id,\n" +
                "\taa.application_no as apply_no,\n" +
                "\taa.applicant_id,\n" +
                "\taa.pet_id,\n" +
                "\tpl.pet_name,\n" +
                "\taa.applyStatus,\n" +
                "\ts.name as statusName,\n" +
                "\taa.created_date,\n" +
                "\taa.approved_by\n" +
                "FROM pethomedev.adopt_application aa\n" +
                "left join application_status s on aa.applyStatus = s.status_id\n" +
                "left join petlist pl on aa.pet_id = pl.pet_id\n" +
                "where applicant_id = :userid\n" +
                "order by :sortBy :orderSeq;";

        HashMap<String, Object> parameters = new HashMap();
        parameters.put("userid", userId);
        switch(sortBy)
        {
            case 1: parameters.put("sortBy" , "created_date");
            break;
            case 2: parameters.put("sortBy" , "applyStatus");
        }
        switch(orderSeq)
        {
            case 1:parameters.put("orderSeq" , " ASC");
            break;
            case -1 :parameters.put("orderSeq" , " DESC");
            break;
        }

        List<ApplicationRecord> record = namedParameterJdbcTemplate.query(sql,parameters , new ApplicationRecordRowMapper());
        if(record.isEmpty())
        {
            return null;
        }else
            return record;
    }



    @Override
    public List<ApplicationRecord> getApplicationByAdmin(Integer status, Integer orderSeq) {

        HashMap<String, Object> parameters = new HashMap();

        String sql = "SELECT\n" +
                "\taa.application_id,\n" +
                "\taa.application_no as apply_no,\n" +
                "\taa.applicant_id,\n" +
                "\taa.pet_id,\n" +
                "\tpl.pet_name,\n" +
                "\taa.applyStatus,\n" +
                "\ts.name as statusName,\n" +
                "\taa.created_date,\n" +
                "\taa.approved_by\n" +
                "FROM pethomedev.adopt_application aa\n" +
                "left join application_status s on aa.applyStatus = s.status_id\n" +
                "left join petlist pl on aa.pet_id = pl.pet_id\n" +
                "where 1=1\n";

        if(status != null)
        {
            sql = sql + " AND aa.applyStatus=:status";
            parameters.put("status", status);
        }

        switch (orderSeq)
        {
            case 1: sql = sql + " ORDER BY aa.created_date ASC ;";
                break;
            case -1 :sql = sql + " ORDER BY aa.created_date DESC ;";
                break;

        }

        List<ApplicationRecord> record = namedParameterJdbcTemplate.query(sql,parameters , new ApplicationRecordRowMapper());
        if(record.isEmpty())
        {
            return null;
        }else
            return record;
    }


    @Override
    public Application getApplicationById(Integer Id) {

        HashMap<String, Object> parameters = new HashMap();
        parameters.put("Id", Id);

        String sql = "select * from adopt_application where application_id = :Id";

        List<Application> result = new ArrayList<Application>();

        try{

            result = namedParameterJdbcTemplate.query(sql, parameters , new ApplicationRowMapper());

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        if(!result.isEmpty() && result.get(0) != null)
        {
            return result.get(0);
        }else{
            return null;
        }
    }


    @Override
    public void updateApplication(Integer appId, Integer userId, Integer status) {

        String sql = "update adopt_application set applyStatus = :status, approved_by = :userId where application_id = :appId";
        HashMap<String, Object> parameters = new HashMap();

        parameters.put("appId", appId);
        parameters.put("userId", userId);
        parameters.put("status", status);

        namedParameterJdbcTemplate.update(sql, parameters);

    }


}
