package com.angus.pethomeadoptionbackend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyNewUserDetails implements UserDetails {


    private final Long userId; // Your user ID field
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public MyNewUserDetails(Long userId, String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // Getters for userId and standard UserDetails methods
    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }



}
