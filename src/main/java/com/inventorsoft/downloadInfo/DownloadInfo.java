package com.inventorsoft.downloadInfo;

import com.inventorsoft.model.user.Admin;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.model.user.User;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DownloadInfo {

    private static final String ADMINS_FILE = "src/main/resources/admins.txt";
    private static final String CUSTOMERS_FILE = "src/main/resources/customers.txt";

    /**
     *
     * @param userType
     * @return List<Customer> or List<Admin>
     * @throws IOException
     */
    public List<? extends User> getInfoFromFile(String userType) throws IOException {

        if (userType.equals("1")) {
            List<Customer> customerList = new ArrayList<>();
            File file = new File(CUSTOMERS_FILE);
            try (FileReader fr = new FileReader(file)) {
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    Customer customer = new Customer();
                    String[] s = line.split(" ");
                    customer.setId(Integer.parseInt(s[0]));
                    customer.setLogin(s[1]);
                    customer.setPassword(s[2]);
                    customer.setEmail(s[3]);
                    customer.setName(s[4]);
                    customerList.add(customer);
                }
                br.close();
                fr.close();
            }
            return customerList;
        }

        if (userType.equals("2")) {
            List<Admin> adminList = new ArrayList<>();
            File file = new File(ADMINS_FILE);
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
            return adminList;
        }
        return null;
    }
}

