package com.angus.pethomeadoptionbackend.model;

import java.util.Date;

public class Gender {

    private Integer id;
    private String gender_name;
    private Date created_date;
    private Date last_modified_date;

    public Gender(Integer id, String gender_name, Date created_date, Date last_modified_date) {
        this.id = id;
        this.gender_name = gender_name;
        this.created_date = created_date;
        this.last_modified_date = last_modified_date;
    }

    public Gender() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(Date last_modified_date) {
        this.last_modified_date = last_modified_date;
    }
}
