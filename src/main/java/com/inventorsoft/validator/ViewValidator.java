package com.inventorsoft.validator;

public class ViewValidator {

    public boolean validateForEqualsInRange(int value, int number) {
        return value >= 1 && value <= number;
    }

}
