package com.angus.pethomeadoptionbackend.model;

import com.angus.pethomeadoptionbackend.dto.PetSearchResponse;

public class ApplicationDetail {

    private User user;
    private PetSearchResponse pet;
    private Integer Status;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PetSearchResponse getPet() {
        return pet;
    }

    public void setPet(PetSearchResponse pet) {
        this.pet = pet;
    }
}
