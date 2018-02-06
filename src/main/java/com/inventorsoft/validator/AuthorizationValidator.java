package com.inventorsoft.validator;


import com.inventorsoft.model.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationValidator {

    //static value for 3 times invalid password;
    static int threeTimes = 3;

    public int checkPasswordThreeTimes() {
        return --threeTimes;
    }

    public boolean validateForOneOrTwo(String value) {
        return value.equals("1") || value.equals("2");
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


    public void validForThreeTimesInvalidPassword() {
        if (checkPasswordThreeTimes() == 0) {
            String answer = "";
            System.out.println("Please recall the password!");
            System.out.println("You want to continue input password?" + "\n"
                    + "1 - yes" + "\n"
                    + "2 - no");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                answer = br.readLine();
                while (validateForOneOrTwo(answer)) {
                    System.out.println("Please input 1 or 2!");
                    answer = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("Problem in validForThreeTimesInvalidPassword" + answer);
            }
            if (answer.equals("2")) {
                System.out.println("Bye-bye, application will be closed");
                System.exit(0);
            }
        }
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
