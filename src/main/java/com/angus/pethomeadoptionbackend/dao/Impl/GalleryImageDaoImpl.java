package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.GalleryImageDao;
import com.angus.pethomeadoptionbackend.model.GalleryImage;
import com.angus.pethomeadoptionbackend.rowMapper.GalleryImageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class GalleryImageDaoImpl implements GalleryImageDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<GalleryImage> getImagesByPetId(Integer petId) {

        HashMap<String, Object> map = new HashMap<>();

        String sqlStatement = "SELECT record_id , petId , imageURL , created_date , last_modified_date FROM pethomedev.petgallery where petId = :petId";

        map.put("petId" , petId);

        List<GalleryImage> result = jdbcTemplate.query(sqlStatement , map , new GalleryImageRowMapper());

        return result;
    }



    @Override
    public void save(GalleryImage galleryImage) {

        String sql = "insert into petgallery(petId , imageURL , created_date , last_modified_date)" +
                "values (:petId , :imageURL , :createdDate , :lastModifiedDate)";

        HashMap<String, Object> map = new HashMap<>();
        map.put("petId" , galleryImage.getPetId());
        map.put("imageURL" , galleryImage.getImageURL());
        map.put("createdDate" , new Date());
        map.put("lastModifiedDate" , new Date());

        jdbcTemplate.update(sql, map);

    }
}



