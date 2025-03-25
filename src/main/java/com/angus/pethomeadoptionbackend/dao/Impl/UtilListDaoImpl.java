package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.UtilListDao;
import com.angus.pethomeadoptionbackend.model.Breed;
import com.angus.pethomeadoptionbackend.model.Gender;
import com.angus.pethomeadoptionbackend.rowMapper.BreedRowMapper;
import com.angus.pethomeadoptionbackend.rowMapper.GenderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class UtilListDaoImpl implements UtilListDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public UtilListDaoImpl() {
        super();
    }

    @Override
    public List<Gender> getGenderDetail() {
        HashMap<String, Object> map = new HashMap<>();
        String sqlStatement = "select * from pet_gender";

        List genderList = namedParameterJdbcTemplate.query(sqlStatement , map, new GenderRowMapper());

        return genderList;
    }

    @Override
    public List<Breed> getBreedDetail() {
        HashMap<String, Object> map = new HashMap<>();
        String sqlStatement = "select * from breed";

        List breedList = namedParameterJdbcTemplate.query(sqlStatement , map, new BreedRowMapper());

        return breedList;
    }
}
