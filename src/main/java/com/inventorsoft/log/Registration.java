package com.inventorsoft.log;

import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.AuthorizationValidator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Registration {
    private static final AuthorizationValidator loginInValidator = new AuthorizationValidator();

    private List<? extends User> userList;

    private User newUser;

    private String fileName;

    private void setUserList(List<? extends User> userList) {
        this.userList = userList;
    }

    private void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getNewUser() {
        return newUser;
    }

    public Registration(User newUser, List<? extends User> userList, final String fileName) throws IOException {
        setNewUser(newUser);
        setUserList(userList);
        setFileName(fileName);

        //auto increment id
        newUser.setId(loginInValidator.autoIncrementId(userList));
        inputLogin();
        inputPassword();
        inputEmail();
        inputName();
        //write in file
        System.out.println(registrationInFile(fileName, newUser));
    }


    private void inputLogin() {
        Scanner scn = new Scanner(System.in);
        //input login
        System.out.println("Input login:");
        String login = scn.next();
        while (true) {
            if (loginInValidator.validateLogin(login)) {
                newUser = loginInValidator.validateForUniqueValue(userList, login, 1);
                if (newUser == null) {
                    break;
                } else {
                    System.out.println("This login already exist!");
                }
            } else {
                System.out.println("Please input correct type of login!");
            }
            login = scn.next();
        }
        newUser.setLogin(login);
    }


    private void inputPassword() {
        //input password
        Scanner scn = new Scanner(System.in);
        System.out.println("Input password:");
        String password = scn.next();
        while (true) {
            if (loginInValidator.validatePassword(password)) {
                break;
            }
            else {
                System.out.println("Wrong password type!");
            }
            password = scn.next();
        }
        newUser.setPassword(password);
    }

    private void inputEmail() {
        //input email
        Scanner scn = new Scanner(System.in);
        System.out.println("Input email:");
        String email = scn.next();
        while (true) {
            if (loginInValidator.validateEmail(email)) {
                newUser = loginInValidator.validateForUniqueValue(userList, email, 2);
                if (newUser == null) {
                    break;
                } else {
                    System.out.println("This email already exist!");
                }
            } else {
                System.out.println("Please input correct type of email!");
            }
            email = scn.next();
        }
        newUser.setEmail(email);
    }

    private void inputName() {
        //input name
        Scanner scn = new Scanner(System.in);
        System.out.println("Input name:");
        String name = scn.next();
        while (true) {
            if (loginInValidator.validateName(name)) {
                break;
            }
            else {
                System.out.println("Wrong name type!");
            }
            name = scn.next();
        }
        newUser.setName(name);
    }

    private String registrationInFile(final String fileName, User user) throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        writer.append(user.toString());
        writer.flush();
        writer.close();
        return "You have successfully registered!";
    }

}
