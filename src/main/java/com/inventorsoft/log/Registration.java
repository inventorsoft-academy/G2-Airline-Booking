package com.inventorsoft.log;

import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.LoginInValidator;
import com.inventorsoft.view.View;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Registration {
    private static final String ADMINS_FILE = "src/com/TextTest/admins.txt";
    private static final String CUSTOMERS_FILE = "src/com/TextTest/customers.txt";
    private static final LoginInValidator loginInValidator = new LoginInValidator();
    private static final View view = new View();

    public Registration(User newUser, List<? extends User> userList, final String fileName) throws IOException {
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
                new Registration(newUser, userList, fileName);
                view.delimiter();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (loginInValidator.validateForUniqueValue(userList,login,"login")) {
            System.out.println("This login already exist, please try again!");
            view.delimiter();
            new Registration(newUser, userList, fileName);
        }

        newUser.setLogin(login);
        //input password
        String password = "";
        System.out.println("Input password:");

        try {
            password = br.readLine();
            if (!loginInValidator.validatePassword(password)) {
                System.out.println("Wrong password type!");
                new Registration(newUser, userList, fileName);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        newUser.setPassword(password);

        //input email
        String email = "";
        System.out.println("Input email:");
        try {
            email = br.readLine();
            if (!loginInValidator.validateEmail(email)) {
                System.out.println("Wrong email type!");
                new Registration(newUser, userList, fileName);
                view.delimiter();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (loginInValidator.validateForUniqueValue(userList,email,"email")) {
            System.out.println("This email already exist, please try again!");
            view.delimiter();
            new Registration(newUser, userList, fileName);
        }

        newUser.setEmail(email);
        //input name
        String name = "";
        System.out.println("Input name:");

        try {
            name = br.readLine();
            if (!loginInValidator.validateName(name)) {
                System.out.println("Wrong password type!");
                new Registration(newUser, userList, fileName);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        newUser.setName(name);
        System.out.println(registrationInFile(fileName,newUser));
    }

    String registrationInFile(final String fileName, User user) throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        writer.append(user.toString());
        writer.flush();
        writer.close();
        return "You have successfully registered!";
    }

}
