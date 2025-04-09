package com.angus.pethomeadoptionbackend.dao.Impl;

import com.angus.pethomeadoptionbackend.dao.FavourListDao;
import com.angus.pethomeadoptionbackend.dto.FavourList;
import com.angus.pethomeadoptionbackend.dto.FavourListWithPet;
import com.angus.pethomeadoptionbackend.rowMapper.FavourListRowMapper;
import com.angus.pethomeadoptionbackend.rowMapper.UserFavourPetRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FavourListDaoImpl implements FavourListDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    //Retrieve the user FavourList detail
    @Override
    public FavourList getUserFavourListById(Integer userId) {

        String sql = "select * from favourlist where user_id= :userId";
        HashMap<String, Object> favourListHashMap = new HashMap<>();
        favourListHashMap.put("userId", userId);

        List<FavourList> returnList = namedParameterJdbcTemplate.query(sql, favourListHashMap, new FavourListRowMapper());

        if(returnList != null && returnList.size() > 0){
            return returnList.get(0);
        }else
            throw new RuntimeException("No User FavourList found in database");
    }


    @Override
    public List<FavourListWithPet> getUserFavourPetList(Integer userId) {

        Integer favourCount = checkHaveFavourPet(userId);
        if(favourCount == 0 || favourCount == null)
        {
            return null;
        }else {

            HashMap<String, Object> favourListHashMap = new HashMap<>();
            favourListHashMap.put("userId", userId);

            String sqlStatement = "select\n" +
                    "usertable.user_id,\n" +
                    "favourlist.favour_list_id,\n" +
                    "favourlist_pet.pet_id,\n" +
                    "favourlist_pet.created_date as Favour_createDate,\n" +
                    "petlist.pet_name,\n" +
                    "TIMESTAMPDIFF(YEAR, petlist.birthday, CURRENT_DATE()) AS age,\n" +
                    "pet_gender.gender_name as gender,\n" +
                    "breed.breed_name as breed,\n" +
                    "petlist.isAvailable\n" +
                    "from usertable\n" +
                    "left join favourlist on usertable.user_id = favourlist.user_id\n" +
                    "left join favourlist_pet on favourlist.favour_list_id = favourlist_pet.favour_list_id\n" +
                    "left join petlist on favourlist_pet.pet_id = petlist.pet_id\n" +
                    "left join pet_gender on petlist.gender = pet_gender.gender_id\n" +
                    "left join breed on petlist.breed = breed.breed_id\n" +
                    "where usertable.user_id = :userId";

            List<FavourListWithPet> result = namedParameterJdbcTemplate.query(sqlStatement, favourListHashMap, new UserFavourPetRowMapper());

            if (result != null && result.size() > 0) {
                return result;
            } else return null;
        }
    }

    @Override
    public void addPetToFavourList(Integer userId, Integer petId) {

        FavourList favourList = getUserFavourListById(userId);
        if(favourList != null){

            Integer userFavourListId = favourList.getFavour_list_Id();

            String sqlStatement = "insert into favourlist_pet(favour_list_id , pet_id , created_date)" +
                    "values(:favourListId, :petId, current_timestamp())";

            Map<String, Object> params = new HashMap<>();
            params.put("favourListId" , userFavourListId);
            params.put("petId" , petId);

            try{

                namedParameterJdbcTemplate.update(sqlStatement, params);
                System.out.println("added pet to favourlist successfully");
            }
            catch (Exception e){
                log.info("Error occour when inserting pet to favour list: " + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }


        }else
            throw new RuntimeException("No favourlist found for user id " + userId);

    }


    @Override
    public void removePetFromFavourList(Integer userId, Integer petId) {

        FavourList favourList = getUserFavourListById(userId);
        if(favourList != null){

            Integer userFavourListId = favourList.getFavour_list_Id();

            String sqlStatement = "delete from favourlist_pet where favour_list_id= :favourListId AND pet_id= :petId";

            Map<String, Object> params = new HashMap<>();
            params.put("favourListId" , userFavourListId);
            params.put("petId" , petId);

            try{

                namedParameterJdbcTemplate.update(sqlStatement, params);
                System.out.println("Deleted pet successfully");
            }
            catch (Exception e){
                log.info("Error occour when inserting pet to favour list: " + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }


        }else
            throw new RuntimeException("No favourlist found for user id " + userId);

    }


    @Override
    public Integer petExistInFavourList(Integer userId, Integer petId) {

        FavourList favourList = getUserFavourListById(userId);
        Integer favourListId = favourList.getFavour_list_Id();

        if(favourList != null){

            String sqlStatement = "select count(*) from favourlist_pet where favour_list_id= :favourListId AND pet_id= :petId";

            HashMap<String, Object> favourListHashMap = new HashMap<>();
            favourListHashMap.put("favourListId", favourListId);
            favourListHashMap.put("petId", petId);


            Integer returnNumber = namedParameterJdbcTemplate.queryForObject(sqlStatement, favourListHashMap, Integer.class);

            return returnNumber;

        }

        return null;
    }


    @Override
    public Integer checkHaveFavourPet(Integer userId) {

        String sql = "select COUNT(*) from favourlist_pet where favour_list_id= :favourListId";

        Integer favourListId = getUserFavourListById(userId).getFavour_list_Id();
        HashMap<String, Object> params = new HashMap<>();
        params.put("favourListId" , favourListId);

        Integer result = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

        return result;

    }


    public void clearAllPetFromFavourList(Integer userId) {

        FavourList favourList = getUserFavourListById(userId);
        Integer favourListId = favourList.getFavour_list_Id();

        if(favourList != null){

            try{

                String sqlStatement = "delete from favourlist_pet where " +
                        "favour_list_id= :favourListId";

                Map<String, Object> params = new HashMap<>();
                params.put("favourListId" , favourListId);

                namedParameterJdbcTemplate.update(sqlStatement, params);

            }
            catch (Exception e){
                System.out.println(e.getMessage());
                throw new RuntimeException("Error occur when delete favourlist record " + e.getMessage());
            }

        }

    }

}
