package com.inventorsoft.log;

import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.LoginInValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Login {

    LoginInValidator loginInValidator = new LoginInValidator();

    private List<? extends User> userList;

    public void setUserList(List<? extends User> userList) {
        this.userList = userList;
    }

    public Login() {
    }

    public Login(List<? extends User> userList) {
        setUserList(userList);

        //while i get no correct info
        while (!checkLogin()) {
            checkLogin();
        }

        while (!checkPassword()) {
            checkPassword();
        }
    }

    private boolean checkLogin() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check login
        String login = "";
        System.out.println("Input login:");
        try {
            login = br.readLine();
            if (!loginInValidator.validateLogin(login)) {
                System.out.println("Please input correct type of login!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!loginInValidator.validateForUniqueValue(userList, login, "login")) {
            System.out.println("This login missing, please complete registration before login!");
            return false;
        }

        return true;

    }

    private boolean checkPassword() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check password
        String password = "";
        System.out.println("Input password:");

        try {
            password = br.readLine();
            if (!loginInValidator.validatePassword(password)) {
                System.out.println("Please input correct type of password!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!loginInValidator.validateForUniqueValue(userList, password, "password")) {
            System.out.println("This password not correct for this login, please try again!");
            //validForThreeTimesInvalidPassword
            loginInValidator.validForThreeTimesInvalidPassword();
            return false;
        }

        return true;
    }

}

