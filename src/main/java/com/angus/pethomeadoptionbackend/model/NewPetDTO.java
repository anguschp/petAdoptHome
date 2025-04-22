package com.angus.pethomeadoptionbackend.model;

public class NewPetDTO{

    private String petName;
    private int petBreed;
    private String petNewBreed;
    private String petBirthday;
    private String petReceivedDate;
    private int petGender;
    private String petDesc;
    private boolean newBreedIndicator;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(int petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetNewBreed() {
        return petNewBreed;
    }

    public void setPetNewBreed(String petNewBreed) {
        this.petNewBreed = petNewBreed;
    }

    public String getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(String petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetReceivedDate() {
        return petReceivedDate;
    }

    public void setPetReceivedDate(String petReceivedDate) {
        this.petReceivedDate = petReceivedDate;
    }

    public int getPetGender() {
        return petGender;
    }

    public void setPetGender(int petGender) {
        this.petGender = petGender;
    }

    public String getPetDesc() {
        return petDesc;
    }

    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

    public boolean isNewBreedIndicator() {
        return newBreedIndicator;
    }

    public void setNewBreedIndicator(boolean newBreedIndicator) {
        this.newBreedIndicator = newBreedIndicator;
    }

    public NewPetDTO() {
    }
}
