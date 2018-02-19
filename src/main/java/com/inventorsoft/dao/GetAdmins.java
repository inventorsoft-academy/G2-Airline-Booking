package com.inventorsoft.dao;

import com.inventorsoft.model.user.Admin;
import com.inventorsoft.validator.UserValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetAdmins implements GetInfo {

    private static final String ADMINS_FILE = "src/main/resources/admins.txt";
    private final UserValidator userValidator = new UserValidator();

    public List<Admin> getInfo() {
        List<Admin> adminList = new ArrayList<>();
        File file = new File(ADMINS_FILE);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (userValidator.validateForAllValuesAdmin(s)) {
                    Admin admin = new Admin();
                    admin.setId(Integer.parseInt(s[0]));
                    admin.setLogin(s[1]);
                    admin.setPassword(s[2]);
                    admin.setEmail(s[3]);
                    adminList.add(admin);
                }
                else {
                    System.out.println("problem in GetAdminsfromFile");
                    System.exit(0);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adminList;
    }
}
