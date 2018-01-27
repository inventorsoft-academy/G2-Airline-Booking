package com.inventorsoft.validator;

public class ViewValidator {

    public boolean validateForNull(String value){
        return !String.valueOf(value).equals("");
    }

    public boolean validateForOneOrTwo(String value) {
        return value.equals("1") || value.equals("2");
    }





}
