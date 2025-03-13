package com.angus.pethomeadoptionbackend.customException;

import java.util.HashMap;
import java.util.Map;

public class ResourceAlreadyExistException extends RuntimeException {

    private HashMap<String , String> validationErrors;

    public ResourceAlreadyExistException(HashMap<String,String> map) {
        super("Validation Errors");
        this.validationErrors = map;
    }

    public HashMap<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(HashMap<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
