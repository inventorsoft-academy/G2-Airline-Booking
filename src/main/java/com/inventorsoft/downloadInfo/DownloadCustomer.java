package com.inventorsoft.downloadInfo;

import com.inventorsoft.model.user.Customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownloadCustomer implements DownloadInfo {

    private static final String CUSTOMERS_FILE = "src/main/resources/customers.txt";


    public static List<Customer> getInfo() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}
