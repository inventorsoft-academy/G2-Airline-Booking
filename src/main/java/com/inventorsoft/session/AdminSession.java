package com.inventorsoft.session;

import com.inventorsoft.log.Login;
import com.inventorsoft.log.Registration;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.offer.OfferOperations;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Admin;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class AdminSession {
    private static final String ADMINS_FILE = "src/main/resources/admins.txt";
    OfferOperations offerOperations = new OfferOperations();

    public AdminSession(List<Admin> adminList, List<Offer> offerList, List<Ticket> ticketList) throws IOException {

        Authorization(adminList);

        //first action
        Action(offerList, ticketList);

        //all others
        while (AskUserToContinueOrExit()) {
            Action(offerList, ticketList);
        }

    }



    void Authorization(List<Admin> adminList) throws IOException {
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
                    new Registration(new Admin(), adminList, ADMINS_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                new Login(adminList);
                break;
        }
    }

    void Action(List<Offer> offerList, List<Ticket> ticketList) throws IOException {
        System.out.println(offerList);
        System.out.println(ticketList);
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an action:" + "\n"
                + "create new offer - 1" + "\n"
                + "view all offers - 2" + "\n"
                + "change needs parameter - 3" + "\n"
                + "delete offer - 4" + "\n"
                + "view balance of bought tickets - 5" + "\n"
                + "to log out - 6");
        int necessaryField = scn.nextInt();
        while (!validateForEqualsInRange(necessaryField, 8)) {
            System.out.println("Please input number from 1 to 6!");
            necessaryField = scn.nextInt();
        }

        switch (necessaryField) {
            case 1:
                System.out.println("info");
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
