package com.inventorsoft.session;

import com.inventorsoft.log.Login;
import com.inventorsoft.log.Registration;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.offer.OfferOperations;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Admin;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.model.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class CustomerSession {
    private static final String CUSTOMERS_FILE = "src/main/resources/customers.txt";
    OfferOperations offerOperations = new OfferOperations();

    public CustomerSession(List<Customer> customerList, List<Offer> offerList, List<Ticket> ticketList) throws IOException {

        Authorization(customerList);

        //first action
        Action(offerList, ticketList);

        //all others
        while (AskUserToContinueOrExit()) {
            Action(offerList, ticketList);
        }

    }



    void Authorization(List<Customer> customerList) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an action:" + "\n"
                + "registration - 1" + "\n"
                + "login - 2");
        int necessaryField = scn.nextInt();
        while (!validateForEqualsInRange(necessaryField, 2)) {
            System.out.println("Please input number 1 or 2!");
            necessaryField = scn.nextInt();
        }

        switch (necessaryField) {
            case 1:
                try {
                    new Registration(new Customer(), customerList, CUSTOMERS_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                new Login(customerList);
                break;
        }
    }

    void Action(List<Offer> offerList, List<Ticket> ticketList) throws IOException {
        System.out.println(offerList);
        System.out.println(ticketList);
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an action:" + "\n"
                + "view all offers - 1" + "\n"
                + "search by needs parameter - 2" + "\n"
                + "book ticket - 3" + "\n"
                + "view all tickets - 4" + "\n"
                + "download ticket - 5" + "\n"
                + "to log out - 6");
        int necessaryField = scn.nextInt();
        while (!validateForEqualsInRange(necessaryField, 8)) {
            System.out.println("Please input number from 1 to 6!");
            necessaryField = scn.nextInt();
        }

        switch (necessaryField) {
            case 1:
                offerOperations.createOffer(offerList);
                break;
            case 2:
                offerOperations.importAllOffers(offerList);
                break;
            case 3:
                try {
                    offerOperations.editOffer(offerList, changeNeedsParameter());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                offerOperations.delete(offerList);
                break;
            case 5:
                //needs ticket
                break;
            case 6:
                offerOperations.exit();
                break;
        }
    }


    public int changeNeedsParameter() throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("What do you want to change?" + "\n"
                + "route - 1" + "\n"
                + "departure time - 2" + "\n"
                + "arrival time - 3" + "\n"
                + "number of seats - 4" + "\n"
                + "price - 5");
        int necessaryField = scn.nextInt();
        while (!validateForEqualsInRange(necessaryField, 5)) {
            System.out.println("Please input number from 1 to 5!");
            necessaryField = scn.nextInt();
        }
        return necessaryField;
    }

    public boolean validateForEqualsInRange(int value, int number) {
        for (int k = 1; k <= number; k++) {
            if (k == value) {
                return true;
            }
        }
        return false;
    }

    public boolean AskUserToContinueOrExit() throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("Do you want to continue work?" + "\n"
                + "Yes, continue - 1" + "\n"
                + "No, logout - 2");
        int necessaryField = scn.nextInt();
        while (!validateForEqualsInRange(necessaryField, 2)) {
            System.out.println("Please input number 1 or 2!");
            necessaryField = scn.nextInt();
        }

        return necessaryField == 1;
    }

}