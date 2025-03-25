package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.Breed;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BreedRowMapper implements RowMapper<Breed> {

    @Override
    public Breed mapRow(ResultSet rs, int rowNum) throws SQLException {

        Breed breed = new Breed();
        breed.setBreedId(rs.getInt("breed_id"));
        breed.setBreedName(rs.getString("breed_name"));
        breed.setCreated_date(rs.getDate("created_date"));
        breed.setLast_modified_date(rs.getDate("last_modified_date"));

        return breed;
    }
}
