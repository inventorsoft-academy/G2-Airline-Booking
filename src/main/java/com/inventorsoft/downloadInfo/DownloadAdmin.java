package com.inventorsoft.downloadInfo;

import com.inventorsoft.model.user.Admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownloadAdmin implements DownloadInfo {

    private static final String ADMINS_FILE = "src/main/resources/admins.txt";


    public static List<Admin> getInfo() {
        List<Admin> adminList = new ArrayList<>();
        File file = new File(ADMINS_FILE);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Admin admin = new Admin();
                String[] s = line.split(" ");
                admin.setId(Integer.parseInt(s[0]));
                admin.setLogin(s[1]);
                admin.setPassword(s[2]);
                admin.setEmail(s[3]);
                adminList.add(admin);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adminList;
    }
}
