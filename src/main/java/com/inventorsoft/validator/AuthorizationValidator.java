package com.inventorsoft.validator;


import com.inventorsoft.model.user.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthorizationValidator {

    public boolean validateForEqualsInRange(int value, int number) {
        return value >= 1 && value <= number;
    }


    public User validateForUniqueValue(List<? extends User> userList, String value, int field) {
        switch (field) {
            case 1:
                for (User user : userList) {
                    if (user.getLogin().equals(value)) {
                        return user;
                    }
                }
            case 2:
                for (User user : userList) {
                    if (user.getEmail().equals(value)) {
                        return user;
                    }
                }
        }
        return null;
    }

    public String getPasswordIfLoginExist(List<? extends User> userList, String login) {
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                return user.getPassword();
            }
        }
        return "";
    }

    public boolean checkForCorrectPassword(String value, String password) {
        return password.equals(value);
    }


    public boolean validateEmail(final String email) {
        Pattern p = Pattern.compile("\\w{6,}@(gmail|mail)\\.(com|ru)");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean validateName(final String name) {
        return name.length() >= 2;
    }

    public boolean validatePassword(final String password) {
        Pattern p = Pattern.compile("[.\\S]{6,}");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean validateLogin(final String login) {
        Pattern p = Pattern.compile("[.\\S]{6,}");
        Matcher m = p.matcher(login);
        return m.matches();
    }


}
