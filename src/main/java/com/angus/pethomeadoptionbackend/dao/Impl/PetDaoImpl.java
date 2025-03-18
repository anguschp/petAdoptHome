package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.rowMapper.PetRecordRowMapper;
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
    public List<PetSearchResponse> getPetProfileList(PetSearchRequest petSearchRequest) {

        HashMap<String, Object> params = new HashMap<>();

        String sqlStatement = "select pl.pet_id as id, pl.serial_no as serialNo,pl.pet_name as name, cat.category_name as category, b.breed_name as breed," +
                                "TIMESTAMPDIFF(YEAR, birthday, CURRENT_DATE()) AS age, g.gender_name as gender, pl.pet_desc as description, pl.received_date, pl.last_modified_date, pl.isAvailable " +
                                "from petlist pl " +
                                "left join petcategory cat on pl.category = cat.id " +
                                "left join breed b on pl.breed = b.breed_id " +
                                "left join pet_gender g on pl.gender = g.gender_id where 1=1";

        if(petSearchRequest.getCategory() != null)
        {
            sqlStatement = sqlStatement + " AND pl.category = :category";
            params.put("category", petSearchRequest.getCategory());
        }

        if(petSearchRequest.getAge() != null)
        {
            sqlStatement = sqlStatement + " AND TIMESTAMPDIFF(YEAR, birthday, CURRENT_DATE()) <= :reqAge";
            params.put("reqAge", petSearchRequest.getAge());
        }

        if(petSearchRequest.getGender() != null)
        {
            sqlStatement = sqlStatement + " AND g.gender_id = :reqGender";
            params.put("reqGender", petSearchRequest.getGender());
        }

        if(petSearchRequest.getBreed() != null)
        {
            sqlStatement = sqlStatement + " AND b.breed_id = :reqBreed";
            params.put("reqBreed", petSearchRequest.getBreed());
        }

        if(petSearchRequest.getName() != null)
        {
            sqlStatement = sqlStatement + " AND pl.pet_name like :reqName";
            params.put("reqName", petSearchRequest.getName());

        }

        if(petSearchRequest.getSerialNo() != null)
        {
            sqlStatement = sqlStatement + " AND pl.serial_no = :reqSerialNo";
            params.put("reqSerialNo" ,petSearchRequest.getSerialNo());
        }

//      To Retrieve the records only is Available right now
        sqlStatement = sqlStatement + " AND pl.isAvailable = :isAvailable";
        params.put("isAvailable" , 1);

        if(petSearchRequest.getLimit() != null)
        {
            sqlStatement = sqlStatement + " LIMIT :limit";
            params.put("limit", petSearchRequest.getLimit());
        }

        List<PetSearchResponse> result = jdbcTemplate.query(sqlStatement , params , new PetRecordRowMapper());

        return result;
    }


}
