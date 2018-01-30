package com.inventorsoft.log;

import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.LoginInValidator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Registration {
    private static final LoginInValidator loginInValidator = new LoginInValidator();

    private List<? extends User> userList;
    private User newUser;
    private String fileName;

    public void setUserList(List<? extends User> userList) {
        this.userList = userList;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Registration(User newUser, List<? extends User> userList, final String fileName) throws IOException {
        setNewUser(newUser);
        setUserList(userList);
        setFileName(fileName);

        //while i get no correct info
        while (!inputLogin()) {
            //wait for correct info
        }

        while (!inputPassword()) {
            //wait for correct info
        }

        while (!inputEmail()) {
            //wait for correct info
        }

        //admin without field "name"
        if (userList.get(1).getName() != null) {
            while (!inputName()) {
                //wait for correct info
            }
        }

        //write in file
        System.out.println(registrationInFile(fileName, newUser));
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private boolean inputLogin() {
        //auto increment id
        newUser.setId(loginInValidator.autoIncrementId(userList));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //input login
        String login = "";
        System.out.println("Input login:");
        try {
            login = br.readLine();
            if (!loginInValidator.validateLogin(login)) {
                System.out.println("Wrong login type!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loginInValidator.validateForUniqueValue(userList, login, "login")) {
            System.out.println("This login already exist, please try again!");
            return false;
        }
        newUser.setLogin(login);
        return true;
    }


    private boolean inputPassword() {
        //input password
        String password = "";
        System.out.println("Input password:");

        try {
            password = br.readLine();
            if (!loginInValidator.validatePassword(password)) {
                System.out.println("Wrong password type!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        newUser.setPassword(password);
        return true;
    }

    private boolean inputEmail() {
        //input email
        String email = "";
        System.out.println("Input email:");
        try {
            email = br.readLine();
            if (!loginInValidator.validateEmail(email)) {
                System.out.println("Wrong email type!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loginInValidator.validateForUniqueValue(userList, email, "email")) {
            System.out.println("This email already exist, please try again!");
            return false;
        }
        newUser.setEmail(email);
        return true;
    }

    private boolean inputName() {
        //input name
        String name = "";
        System.out.println("Input name:");

        try {
            name = br.readLine();
            if (!loginInValidator.validateName(name)) {
                System.out.println("Wrong name type!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        newUser.setName(name);
        return true;
    }

    private String registrationInFile(final String fileName, User user) throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        writer.append(user.toString());
        writer.flush();
        writer.close();
        return "You have successfully registered!";
    }

}
