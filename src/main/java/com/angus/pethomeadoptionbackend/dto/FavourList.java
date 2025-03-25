package com.angus.pethomeadoptionbackend.dto;

import java.util.Date;

public class FavourList {

    private Integer favour_list_Id;
    private Integer user_id;
    private Date date;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getFavour_list_Id() {
        return favour_list_Id;
    }

    public void setFavour_list_Id(Integer favour_list_Id) {
        this.favour_list_Id = favour_list_Id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
