package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFavourPetRowMapper implements RowMapper<FavourListWithPet> {

    @Override
    public FavourListWithPet mapRow(ResultSet rs, int rowNum) throws SQLException {

        FavourListWithPet favourListWithPet = new FavourListWithPet();

        favourListWithPet.setId(rs.getInt("user_id"));
        favourListWithPet.setFavourListId(rs.getInt("favour_list_id"));
        favourListWithPet.setPet_id(rs.getInt("pet_id"));
        favourListWithPet.setFavourCreateDate(rs.getDate("Favour_createDate"));
        favourListWithPet.setPetName(rs.getString("pet_name"));
        favourListWithPet.setAge(rs.getInt("age"));
        favourListWithPet.setGender(rs.getString("gender"));
        favourListWithPet.setBreed(rs.getString("breed"));
        favourListWithPet.setPetStatus(rs.getInt("isAvailable"));


        return favourListWithPet;
    }
}
