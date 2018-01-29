package com.inventorsoft.console;

import com.inventorsoft.downloadInfo.DownloadInfo;
import com.inventorsoft.log.Login;
import com.inventorsoft.log.Registration;
import com.inventorsoft.model.user.Admin;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.model.user.User;
import com.inventorsoft.view.View;

import java.io.IOException;
import java.util.List;

public class Console {
    private static final String ADMINS_FILE = "src/main/resources/admins.txt";
    private static final String CUSTOMERS_FILE = "src/main/resources/customers.txt";
    private static final View view = new View();
    private static final DownloadInfo info = new DownloadInfo();
    private static List<? extends User> userList;

    public static void main(String[] args) throws IOException {
        view.welcome();
        String answer = view.howToWork();
        System.out.println(answer);
        userList = info.getUsersFromFile(answer);
        view.delimiter();
        answer += view.registrationOrLogin();
        System.out.println(answer);
        loginInUser(answer, userList);

    }

    public static void loginInUser(String answer, List<? extends User> userList) {
        switch (answer) {
            case "11":
                try {
                    new Registration(new Customer(), userList, CUSTOMERS_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "12":
                new Login(userList);
                break;
            case "21":
                try {
                    new Registration(new Admin(), userList, ADMINS_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "22":
                new Login(userList);
                break;
        }
    }
}
