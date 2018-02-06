package com.inventorsoft.getInfoFromFile;

import com.inventorsoft.model.user.Customer;
import com.inventorsoft.validator.UserValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCustomersFromFile implements GetInfoFromFile {

    private static final String CUSTOMERS_FILE = "resources/customers.txt";
    private final UserValidator userValidator = new UserValidator();

    public List<Customer> getInfo() {
        List<Customer> customerList = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {

                String[] s = line.split(" ");
                if (userValidator.validateId(s[0]) && userValidator.validateForUniqueUserId(s[0])
                        && userValidator.validateLogin(s[1]) && userValidator.validateForUniqueLogin(s[1])
                        && userValidator.validatePassword(s[2])
                        && userValidator.validateEmail(s[3]) && userValidator.validateForUniqueEmail(s[3])
                        && userValidator.validateName(s[4])) {
                    Customer customer = new Customer();
                    customer.setId(Integer.parseInt(s[0]));
                    customer.setLogin(s[1]);
                    customer.setEmail(s[3]);
                    customer.setPassword(s[2]);
                    customer.setName(s[4]);
                    customerList.add(customer);
                }
                else {
                    System.out.println("problem in GetCustomerfromFile");
                    System.exit(0);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}
