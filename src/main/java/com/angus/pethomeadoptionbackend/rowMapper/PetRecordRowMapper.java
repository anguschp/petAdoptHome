package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.Pet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetRecordRowMapper implements RowMapper<Pet> {

    @Override
    public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {

        Pet pet = new Pet();
        pet.setPet_id(rs.getInt("pet_id"));
        pet.setSerial_number(rs.getString("serial_no"));
        pet.setName(rs.getString("pet_name"));
        pet.setPet_category(rs.getInt("category"));
        pet.setBreed(rs.getInt("breed"));
        pet.setBirthday(rs.getDate("birthday"));
        pet.setAge(rs.getInt("age"));
        pet.setGender(rs.getInt("gender"));
        pet.setReceived_date(rs.getDate("received_date"));
        pet.setLast_modified_date(rs.getDate("last_modified_date"));
        pet.setPet_description(rs.getString("pet_desc"));





        return null;
    }
}
