package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.dto.FavourList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavourListRowMapper implements RowMapper<FavourList> {

    @Override
    public FavourList mapRow(ResultSet rs, int rowNum) throws SQLException {

        FavourList favourList = new FavourList();

        favourList.setFavour_list_Id(rs.getInt("favour_list_id"));
        favourList.setUser_id(rs.getInt("user_id"));
        favourList.setDate(rs.getDate("created_date"));

        return favourList;
    }
}
