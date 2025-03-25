package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderRowMapper implements RowMapper<Gender> {


    @Override
    public Gender mapRow(ResultSet rs, int rowNum) throws SQLException {
        Gender gender = new Gender();
        gender.setId(rs.getInt("gender_id"));
        gender.setGender_name(rs.getString("gender_name"));
        gender.setCreated_date(rs.getDate("created_date"));
        gender.setLast_modified_date(rs.getDate("last_modified_date"));

        return gender;
    }
}
