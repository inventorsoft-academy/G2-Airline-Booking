package com.inventorsoft.log;

import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.AuthorizationValidator;
import java.util.List;
import java.util.Scanner;

public class Login {

    private AuthorizationValidator loginInValidator = new AuthorizationValidator();

    private List<? extends User> userList;

    private User newUser;

    private String login = "";

    private void setUserList(List<? extends User> userList) {
        this.userList = userList;
    }

    private void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public User getNewUser() {
        return newUser;
    }


    public Login() {
    }

    public Login(List<? extends User> userList, User newUser) {
        setUserList(userList);
        setNewUser(newUser);
        //check user login and password
        checkLogin();
        checkPassword();
        setAllFieldsInNewUser(login);
    }

    private void checkLogin() {
        Scanner scn = new Scanner(System.in);
        //check login
        System.out.println("Input login:");
        login = scn.next();
        while (true) {
            if (loginInValidator.validateLogin(login)) {
                newUser = loginInValidator.validateForUniqueValue(userList, login, 1);
                if (newUser != null) {
                    break;
                } else {
                    System.out.println("This login missing, please complete registration before login or try again!");
                }
            } else {
                System.out.println("Please input correct type of login!");
            }
            login = scn.next();
        }
    }

    private void checkPassword() {
        Scanner scn = new Scanner(System.in);
        //check password
        System.out.println("Input password:");
        String password = scn.next();
        while (true) {
            if (loginInValidator.validatePassword(password)) {
                if (loginInValidator.validateForUniqueValue(password, newUser.getPassword())) {
                    break;
                } else {
                    System.out.println("This password not correct for this login, please try again!");
                    //validForThreeTimesInvalidPassword
                    loginInValidator.validForThreeTimesInvalidPassword();
                }
            }
            else {
                System.out.println("Please input correct type of password!");
            }
            password = scn.next();
        }
    }

    private void setAllFieldsInNewUser(String login) {
        //save if user exist in file
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                newUser.setId(user.getId());
                newUser.setLogin(user.getLogin());
                newUser.setPassword(user.getPassword());
                newUser.setEmail(user.getEmail());
                newUser.setName(user.getName());
            }
        }
    }
}

