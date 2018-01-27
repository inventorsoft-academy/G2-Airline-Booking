package com.inventorsoft.validator;


import com.inventorsoft.model.user.User;
import com.inventorsoft.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class LoginInValidator {

    //static value for 3 times invalid password;
    static int threeTimes = 3;

    public int checkPasswordThreeTimes() {
        return --threeTimes;
    }

    public boolean validateForNull(String value){
        return !String.valueOf(value).equals("");
    }

    public boolean validateForOneOrTwo(String value) {
        return value.equals("1") || value.equals("2");
    }


    //specific user for check password
    String specificUserPassword = "";
    public boolean validateForUniqueValue(List<? extends User> userList, String value, String field) {
        switch (field) {
            case "login":
                for (User user: userList) {
                    if (user.getLogin().equals(value)) {
                        specificUserPassword = user.getPassword();
                        System.out.println(specificUserPassword);
                        return true;
                    }
                }
            case "password":
                return specificUserPassword.equals(value);
            case "email":
                for (User user: userList) {
                    if (user.getEmail().equals(value)){
                        return true;
                    }
                }

        }
        return false;
    }

    public void validForThreeTimesInvalidPassword() {
        if (checkPasswordThreeTimes() == 0) {
            String answer = "";
            System.out.println("Please recall the password!");
            View view = new View();
            view.delimiter();
            System.out.println("You want to continue input password?" + "\n"
                    + "1 - yes" + "\n"
                    + "2 - no");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                answer = br.readLine();
                if (!validateForNull(answer) || !validateForOneOrTwo(answer)) {
                    System.out.println("Please input 1 or 2!");
                    validForThreeTimesInvalidPassword();
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

    public int autoIncrementId(final List<? extends User> userList) {
        int maxId = 0;
        for (User user: userList) {
            if (maxId < user.getId()) {
                maxId = user.getId();
            }

        }
        return ++maxId;
    }

    public boolean validateEmail(String email) {
        String[] prefixEmail = email.split("@",1);
        return email.contains("@") && email.contains("mail.") && prefixEmail[0].length() >= 6;
    }

    public boolean validateName(String name) {
        return name.length() >= 2;
    }

    public boolean validatePassword(String password) {
        return password.length() >= 6;
    }

    public boolean validateLogin(String login) {
        return login.length() >= 6;
    }
}
