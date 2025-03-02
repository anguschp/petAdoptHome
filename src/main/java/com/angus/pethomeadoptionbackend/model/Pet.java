package com.angus.pethomeadoptionbackend.model;

import com.angus.pethomeadoptionbackend.constant.Gender;

import java.util.Date;
import java.util.List;

public class Pet {

    private int pet_id;
    private String serial_number;
    private String name;
    private int pet_category;
    private int breed;
    private Date birthday;
    private Gender gender;
    private String pet_description;
    private Date received_date;
    private Date last_modified_date;
    private List<String> imageURL;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPet_category() {
        return pet_category;
    }

    public void setPet_category(int pet_category) {
        this.pet_category = pet_category;
    }

    public int getBreed() {
        return breed;
    }

    public void setBreed(int breed) {
        this.breed = breed;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPet_description() {
        return pet_description;
    }

    public void setPet_description(String pet_description) {
        this.pet_description = pet_description;
    }

    public Date getReceived_date() {
        return received_date;
    }

    public void setReceived_date(Date received_date) {
        this.received_date = received_date;
    }

    public Date getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(Date last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    public List<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<String> imageURL) {
        this.imageURL = imageURL;
    }
}
