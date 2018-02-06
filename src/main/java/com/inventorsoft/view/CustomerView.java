package com.inventorsoft.view;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.validator.ViewValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private static final String CUSTOMERS_FILE = "resources/customers.txt";
    OfferView offerView = new OfferView();
    TicketView ticketView = new TicketView();

    ViewValidator validator = new ViewValidator();
    AuthorizationView authorizationView = new AuthorizationView();
    Customer customer;

    public CustomerView(List<Customer> customerList, List<Offer> offerList, List<Ticket> ticketList) throws IOException {

        authorization(customerList);

        //first action
        action(offerList, ticketList);

        //all others
        while (AskUserToContinueOrExit()) {
            action(offerList, ticketList);
        }

    }


    void authorization(List<Customer> customerList) {
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
                    authorizationView.registration(new Customer(), customerList, CUSTOMERS_FILE, true);
                    customer = authorizationView.authorizationController.getCustomer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                authorizationView.login(new Customer(), customerList, true);
                customer = authorizationView.authorizationController.getCustomer();
                break;
        }
    }

    void action(List<Offer> offerList, List<Ticket> ticketList) throws IOException {
        System.out.println(offerList);
        System.out.println(ticketList);
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an action:" + "\n"
                + "view all offers - 1" + "\n"
                + "search by needs parameter - 2" + "\n"
                + "book ticket - 3" + "\n"
                + "view all ordered tickets - 4" + "\n"
                + "download ticket - 5" + "\n"
                + "to log out - 6");
        int necessaryField = scn.nextInt();
        while (!validator.validateForEqualsInRange(necessaryField, 8)) {
            System.out.println("Please input number from 1 to 6!");
            necessaryField = scn.nextInt();
        }

        switch (necessaryField) {
            case 1:
                offerView.importAllOffers(offerList);
                break;
            case 2:
                offerView.searchOffer(offerList);
                break;
            case 3:
                ticketView.bookTicket(offerList, customer);
                break;
            case 4:
                ticketView.viewAllOrderedTickets(ticketList, customer);
                break;
            case 5:
                ticketView.downloadTicket(offerList, ticketList, customer);
                break;
            case 6:
                authorizationView.exit();
                break;
        }
    }


    private boolean AskUserToContinueOrExit() {
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
