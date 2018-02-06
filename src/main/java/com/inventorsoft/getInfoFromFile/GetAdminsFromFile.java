package com.inventorsoft.getInfoFromFile;

import com.inventorsoft.model.user.Admin;
import com.inventorsoft.validator.UserValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetAdminsFromFile implements GetInfoFromFile {

    private static final String ADMINS_FILE = "resources/admins.txt";
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
                if (userValidator.validateId(s[0]) && userValidator.validateForUniqueUserId(s[0])
                        && userValidator.validateLogin(s[1]) && userValidator.validateForUniqueLogin(s[1])
                        && userValidator.validatePassword(s[2])
                        && userValidator.validateEmail(s[3]) && userValidator.validateForUniqueEmail(s[3])) {
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
