package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.PetDao;
import com.angus.pethomeadoptionbackend.dto.PetSearchRequest;
import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;
import com.angus.pethomeadoptionbackend.model.NewPetDTO;
import com.angus.pethomeadoptionbackend.model.Pet;
import com.angus.pethomeadoptionbackend.rowMapper.PetRecordRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PetDaoImpl implements PetDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String ALPHANUMERIC =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

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


    @Override
    public PetSearchResponse getAvailablePetById(Integer id) {

        HashMap<String, Object> params = new HashMap<>();
        String sqlStatement = "select pl.pet_id as id, pl.serial_no as serialNo,pl.pet_name as name, cat.category_name as category, b.breed_name as breed," +
                "TIMESTAMPDIFF(YEAR, birthday, CURRENT_DATE()) AS age, g.gender_name as gender, pl.pet_desc as description, pl.received_date, pl.last_modified_date, pl.isAvailable " +
                "from petlist pl " +
                "left join petcategory cat on pl.category = cat.id " +
                "left join breed b on pl.breed = b.breed_id " +
                "left join pet_gender g on pl.gender = g.gender_id where pl.pet_id = :petId AND pl.isAvailable = :isAvailable";

        params.put("petId", id);
        params.put("isAvailable", 1);

        List<PetSearchResponse> result = jdbcTemplate.query(sqlStatement , params , new PetRecordRowMapper());

        if(result != null && !result.isEmpty())
        {
            return result.get(0);
        }else  return null;
    }


    @Override
    public PetSearchResponse getPetById(Integer id) {

        HashMap<String, Object> params = new HashMap<>();
        String sqlStatement = "select pl.pet_id as id, pl.serial_no as serialNo,pl.pet_name as name, cat.category_name as category, b.breed_name as breed," +
                "TIMESTAMPDIFF(YEAR, birthday, CURRENT_DATE()) AS age, g.gender_name as gender, pl.pet_desc as description, pl.received_date, pl.last_modified_date, pl.isAvailable " +
                "from petlist pl " +
                "left join petcategory cat on pl.category = cat.id " +
                "left join breed b on pl.breed = b.breed_id " +
                "left join pet_gender g on pl.gender = g.gender_id where pl.pet_id = :petId";

        params.put("petId", id);

        List<PetSearchResponse> result = jdbcTemplate.query(sqlStatement , params , new PetRecordRowMapper());

        if(result != null && !result.isEmpty())
        {
            return result.get(0);
        }else  return null;
    }


    @Override
    public void updatePetAvailable(Integer id, Integer available) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("petId", id);
        params.put("isAvailable", available);

        String sql = "update petlist set isAvailable = :isAvailable where pet_id = :petId";

        namedParameterJdbcTemplate.update(sql, params);
    }


    @Override
    public Integer addPet(NewPetDTO pet) {

        String sql = "INSERT INTO petlist(" +
                "serial_no, category, pet_name, breed, birthday, " +
                "gender, received_date, last_modified_date, pet_desc, isAvailable) " +
                "VALUES(" +
                ":petSerial, :category, :petName, :petBreed, :petBirthday, " +
                ":petGender, :petReceivedDate, CURRENT_TIMESTAMP, :petDesc, 1)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            sb.append(ALPHANUMERIC.charAt(
                    random.nextInt(ALPHANUMERIC.length())));
        }
        String tempUUID = sb.toString().concat(pet.getPetName().substring(0,1));
        params.addValue("petSerial", tempUUID);
        params.addValue("category", 1);
        params.addValue("petName", pet.getPetName());
        params.addValue("petBreed", pet.getPetBreed());

// Handle dates properly
        params.addValue("petBirthday", convertIsoToTimestamp(pet.getPetBirthday()));
        params.addValue("petGender", pet.getPetGender());
        params.addValue("petReceivedDate", convertIsoToTimestamp(pet.getPetReceivedDate()));
        params.addValue("petDesc", pet.getPetDesc());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        return keyHolder.getKey().intValue();

    }


    public static Timestamp convertIsoToTimestamp(String isoDateTime) {
        return Timestamp.from(Instant.parse(isoDateTime));
    }


}
