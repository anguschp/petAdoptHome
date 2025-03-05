package com.angus.pethomeadoptionbackend.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "appuser")
public class ApplicationUser implements UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer userId;
    private String username;
    private String password;



}
