package com.inventorsoft.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private String emails = "";
    private String logins = "";
    private String usersId = "";

    public boolean validateForAllValuesAdmin(String[] s) {
        return validateId(s[0]) && validateForUniqueUserId(s[0])
                && validateLogin(s[1]) && validateForUniqueLogin(s[1])
                && validatePassword(s[2])
                && validateEmail(s[3]) && validateForUniqueEmail(s[3]);
    }

    public boolean validateForAllValuesOffer(String[] s) {
        return validateId(s[0]) && validateForUniqueUserId(s[0])
                && validateLogin(s[1]) && validateForUniqueLogin(s[1])
                && validatePassword(s[2])
                && validateEmail(s[3]) && validateForUniqueEmail(s[3])
                && validateName(s[4]);
    }




    public boolean validateForUniqueUserId(final String id) {
        if (!usersId.contains(id)) {
            usersId += id + ",";
            return true;
        }
        return false;
    }

    public boolean validateForUniqueEmail(final String email) {
        if (!emails.contains(email)) {
            emails += email + ",";
            return true;
        }
        return false;
    }

    public boolean validateForUniqueLogin(final String login) {
        if (!logins.contains(login)) {
            logins += login + ",";
            return true;
        }
        return false;
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

    public boolean validateId(final String id) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(id);
        return m.matches();
    }





}
