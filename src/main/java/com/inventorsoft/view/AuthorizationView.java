package com.inventorsoft.view;

import com.inventorsoft.controller.AuthorizationController;
import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.AuthorizationValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AuthorizationView {

    private AuthorizationValidator authorizationValidator = new AuthorizationValidator();

    public AuthorizationController authorizationController = new AuthorizationController();

    private String password;

    public void login(User newUser, List<? extends User> userList, boolean checkName) {

        authorizationController.login(newUser, checkLogin(userList), userList, checkName);
        checkPassword();

    }

    private String checkLogin(List<? extends User> userList) {
        System.out.println(userList);
        Scanner scn = new Scanner(System.in);
        //check login
        System.out.println("Input login:");
        String login = scn.next();
        while (true) {
            if (authorizationValidator.validateLogin(login)) {
                password = authorizationValidator.getPasswordIfLoginExist(userList, login);
                if (!password.equals("")) {
                    break;
                } else {
                    System.out.println("This login missing, please complete registration before login or try again!");
                }
            } else {
                System.out.println("Please input correct type of login!");
            }
            login = scn.next();
        }
        return login;
    }

    private void checkPassword() {
        Scanner scn = new Scanner(System.in);
        //check password
        System.out.println("Input password:");
        String password = scn.next();
        while (true) {
            if (authorizationValidator.validatePassword(password)) {
                if (authorizationValidator.checkForCorrectPassword(password, this.password)) {
                    break;
                } else {
                    System.out.println("This password not correct for this login, please try again!");
                    //validForThreeTimesInvalidPassword
                    authorizationValidator.validForThreeTimesInvalidPassword();
                }
            } else {
                System.out.println("Please input correct type of password!");
            }
            password = scn.next();
        }
    }

    public void registration(User newUser,
                             List<? extends User> userList,
                             final String fileName,
                             boolean checkName) throws IOException {

        authorizationController.registration(inputLogin(userList),
                inputPassword(),
                inputEmail(userList),
                inputName(checkName),
                newUser,
                userList,
                fileName,
                checkName);
    }


    private String inputLogin(List<? extends User> userList) {
        Scanner scn = new Scanner(System.in);
        //input login
        System.out.println("Input login:");
        String login = scn.next();
        while (true) {
            if (authorizationValidator.validateLogin(login)) {
                if (authorizationValidator.validateForUniqueValue(userList, login, 1) == null) {
                    break;
                } else {
                    System.out.println("This login already exist!");
                }
            } else {
                System.out.println("Please input correct type of login!");
            }
            login = scn.next();
        }
        return login;
    }


    private String inputPassword() {
        //input password
        Scanner scn = new Scanner(System.in);
        System.out.println("Input password:");
        String password = scn.next();
        while (true) {
            if (authorizationValidator.validatePassword(password)) {
                break;
            } else {
                System.out.println("Wrong password type!");
            }
            password = scn.next();
        }
        return password;
    }

    private String inputEmail(List<? extends User> userList) {
        //input email
        Scanner scn = new Scanner(System.in);
        System.out.println("Input email:");
        String email = scn.next();
        while (true) {
            if (authorizationValidator.validateEmail(email)) {
                if (authorizationValidator.validateForUniqueValue(userList, email, 2) == null) {
                    break;
                } else {
                    System.out.println("This email already exist!");
                }
            } else {
                System.out.println("Please input correct type of email!");
            }
            email = scn.next();
        }
        return email;
    }

    private String inputName(boolean checkName) {
        String name = "";
        if (checkName) {
            //input name
            Scanner scn = new Scanner(System.in);
            System.out.println("Input name:");
            name = scn.next();
            while (true) {
                if (authorizationValidator.validateName(name)) {
                    break;
                } else {
                    System.out.println("Wrong name type!");
                }
                name = scn.next();
            }
        }
        return name;
    }

    //exit method
    public void exit() {
        System.out.println("Bye-bye, application will be closed");
        authorizationController.exit();
    }

}
