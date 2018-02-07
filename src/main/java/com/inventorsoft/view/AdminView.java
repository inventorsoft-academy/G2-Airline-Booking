package com.inventorsoft.view;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Admin;
import com.inventorsoft.validator.ViewValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.inventorsoft.console.Console.logger;

public class AdminView {
    private static final String ADMINS_FILE = "resources/admins.txt";

    OfferView offerView = new OfferView();
    TicketView ticketView = new TicketView();

    ViewValidator validator = new ViewValidator();
    AuthorizationView authorizationView = new AuthorizationView();

    public AdminView(List<Admin> adminList, List<Offer> offerList, List<Ticket> ticketList) throws IOException {

        authorization(adminList);

        //first action
        action(offerList, ticketList);

        //all others
        while (AskUserToContinueOrExit()) {
            action(offerList, ticketList);
        }

    }


    void authorization(List<Admin> adminList) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an action:" + "\n"
                + "registration - 1" + "\n"
                + "login - 2");
        int necessaryField = scn.nextInt();
        while (!validator.validateForEqualsInRange(necessaryField, 2)) {
            System.out.println("Please input number 1 or 2!");
            necessaryField = scn.nextInt();
        }

        switch (necessaryField) {
            case 1:
                try {
                    authorizationView.registration(new Admin(), adminList, ADMINS_FILE, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                authorizationView.login(new Admin(), adminList, false);
                break;
        }
    }

    void action(List<Offer> offerList, List<Ticket> ticketList) throws IOException {
        logger.info("beginning offer list:");
        logger.info(offerList.toString());
        logger.info("beginning ticket list:");
        logger.info(ticketList.toString());
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an action:" + "\n"
                + "create new offer - 1" + "\n"
                + "view all offers - 2" + "\n"
                + "change needs parameter - 3" + "\n"
                + "delete offer - 4" + "\n"
                + "view balance of bought tickets - 5" + "\n"
                + "to log out - 6");
        int necessaryField = scn.nextInt();
        while (!validator.validateForEqualsInRange(necessaryField, 6)) {
            System.out.println("Please input number from 1 to 6!");
            necessaryField = scn.nextInt();
        }

        switch (necessaryField) {
            case 1:
                offerView.createOffer(offerList);
                break;
            case 2:
                offerView.importAllOffers(offerList);
                break;
            case 3:
                offerView.editOffer(offerList, changeNeedsParameter());
                break;
            case 4:
                offerView.delete(offerList);
                break;
            case 5:
                ticketView.balanceOfBoughtTickets(ticketList);
                break;
            case 6:
                authorizationView.exit();
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
        while (!validator.validateForEqualsInRange(necessaryField, 5)) {
            System.out.println("Please input number from 1 to 5!");
            necessaryField = scn.nextInt();
        }
        return necessaryField;
    }

    public boolean AskUserToContinueOrExit() throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("Do you want to continue work?" + "\n"
                + "Yes, continue - 1" + "\n"
                + "No, logout - 2");
        int necessaryField = scn.nextInt();
        while (!validator.validateForEqualsInRange(necessaryField, 2)) {
            System.out.println("Please input number 1 or 2!");
            necessaryField = scn.nextInt();
        }

        return necessaryField == 1;
    }
}
