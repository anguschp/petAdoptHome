package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.GalleryImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GalleryImageRowMapper implements RowMapper<GalleryImage> {


    @Override
    public GalleryImage mapRow(ResultSet rs, int rowNum) throws SQLException {

        GalleryImage galleryImage = new GalleryImage();
        galleryImage.setId(rs.getInt("record_id"));
        galleryImage.setPetId(rs.getInt("petId"));
        galleryImage.setImageURL(rs.getString("imageURL"));
        galleryImage.setCreated_date(rs.getDate("created_date"));
        galleryImage.setLast_modified_date(rs.getDate("last_modified_date"));

        return galleryImage;
    }

}
