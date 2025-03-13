package com.angus.pethomeadoptionbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "usertable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userid;

    @Column(name = "user_name")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "created_date")
    private Date created_date;

    @Column(name = "last_modified_date")
    private Date last_modified_date;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
