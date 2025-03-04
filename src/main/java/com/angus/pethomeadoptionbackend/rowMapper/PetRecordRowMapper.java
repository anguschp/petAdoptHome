package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetRecordRowMapper implements RowMapper<PetSearchResponse> {

    @Override
    public PetSearchResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

        PetSearchResponse pet = new PetSearchResponse();
        pet.setPet_id(rs.getInt("id"));
        pet.setSerial_number(rs.getString("serialNo"));
        pet.setName(rs.getString("name"));
        pet.setPet_category(rs.getString("category"));
        pet.setBreed(rs.getString("breed"));
        pet.setAge(rs.getInt("age"));
        pet.setGender(rs.getString("gender"));
        pet.setPet_description(rs.getString("description"));
        pet.setReceived_date(rs.getDate("received_date"));
        pet.setLast_modified_date(rs.getDate("last_modified_date"));


        return pet;
    }
}
