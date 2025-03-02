package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class PetDaoImpl implements PetDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Pet> getPetProfileList(PetSearchRequest petSearchRequest) {

        HashMap<String, Object> params = new HashMap<>();

        String sqlStatement = "SELECT  pet_id,  serial_no, category,  pet_name,  breed," +
                "TIMESTAMPDIFF(YEAR, birthday, CURRENT_DATE()) AS age, gender, pet_desc,  received_date, last_modified_date " +
                "FROM pethomedev.petlist where 1=1";

        if(petSearchRequest.getAge() != null)
        {
            sqlStatement = sqlStatement + " AND age <= :reqAge";
            params.put("reqAge", petSearchRequest.getAge());
        }

        if(petSearchRequest.getGender() != null)
        {
            sqlStatement = sqlStatement + " AND gen.gender_id = :reqGender";
            params.put("reqGender", petSearchRequest.getGender());
        }

        if(petSearchRequest.getBreed() != null)
        {
            sqlStatement = sqlStatement + " AND breed_id = :reqBreed";
            params.put("reqBreed", petSearchRequest.getBreed());
        }

        if(petSearchRequest.getPet_id() != null)
        {
            sqlStatement = sqlStatement + " AND pl.pet_id = :pet_id";
            params.put("pet_id", petSearchRequest.getPet_id());
        }

        if(petSearchRequest.getSerial_no() != null)
        {
            sqlStatement = sqlStatement + " AND serial_no = :serial_no";
            params.put("serial_no", petSearchRequest.getSerial_no());
        }

        if(petSearchRequest.getLimit() != null)
        {
            sqlStatement = sqlStatement + " LIMIT :limit";
            params.put("limit", petSearchRequest.getLimit());
        }

        jdbcTemplate.query(sqlStatement , params , new PetRecordRowMapper());

    }


}
