package com.angus.pethomeadoptionbackend.constant;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Gender {

    MALE("male", 1),
    FEMALE("female", 2),
    NA("na" , 3);

    private final String name;
    private final int value;


}