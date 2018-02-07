package com.inventorsoft.controller;

import com.inventorsoft.model.user.Customer;
import com.inventorsoft.model.user.User;
import com.inventorsoft.setInfoToFile.SetModelToFile;

import java.io.IOException;
import java.util.List;

import static com.inventorsoft.console.Console.logger;

public class AuthorizationController {

    private SetModelToFile setModelToFile = new SetModelToFile();

    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void login(User user, String login, List<? extends User> userList, boolean checkName) {
        for (User specificUser : userList) {
            if (specificUser.getLogin().equals(login)) {
                user.setId(specificUser.getId());
                user.setLogin(specificUser.getLogin());
                user.setPassword(specificUser.getPassword());
                user.setEmail(specificUser.getEmail());
                user.setName(specificUser.getName());
            }
        }

        if (checkName) {
            this.customer = (Customer) user;
        }
    }

    public void registration(String login,
                             String password,
                             String email,
                             String name,
                             User user,
                             List<? extends User> userList,
                             String fileName,
                             boolean checkName) {


        user.setId(autoIncrementId(userList));
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);


        if (checkName) {
            this.customer = (Customer) user;
        }

        try {
            setModelToFile.setInfo(user, fileName);
            logger.info("correct write new user im file " + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int autoIncrementId(final List<? extends User> userList) {
        int maxId = 0;
        for (User user : userList) {
            if (maxId < user.getId()) {
                maxId = user.getId();
            }

        }
        return ++maxId;
    }

    public void exit() {
        System.exit(0);
    }
}
