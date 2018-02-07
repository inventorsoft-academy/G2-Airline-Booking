package com.inventorsoft.view;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.validator.ViewValidator;
import static com.inventorsoft.console.Console.logger;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
public class CustomerView {
    private static final String CUSTOMERS_FILE = "resources/customers.txt";
    private OfferView offerView = new OfferView();
    private TicketView ticketView = new TicketView();
    private ViewValidator validator = new ViewValidator();
    private AuthorizationView authorizationView = new AuthorizationView();
    private Customer customer;

    public CustomerView(List<Customer> customerList,
                        List<Offer> offerList,
                        List<Ticket> ticketList) throws IOException {

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
                    logger.info("Customer choose a registration");
                    authorizationView.registration(new Customer(), customerList, CUSTOMERS_FILE, true);
                    customer = authorizationView.authorizationController.getCustomer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                logger.info("Customer choose a login");
                authorizationView.login(new Customer(), customerList, true);
                customer = authorizationView.authorizationController.getCustomer();
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
                logger.info("Customer choose import all offers");
                offerView.importAllOffers(offerList);
                break;
            case 2:
                logger.info("Customer choose search all offers");
                offerView.searchOffer(offerList);
                break;
            case 3:
                logger.info("Customer choose book a ticket");
                ticketView.bookTicket(offerList, customer);
                break;
            case 4:
                logger.info("Customer choose view all ordered tickets");
                ticketView.viewAllOrderedTickets(ticketList, customer);
                break;
            case 5:
                logger.info("Customer choose download ticket");
                ticketView.downloadTicket(offerList, ticketList, customer);
                break;
            case 6:
                logger.info("Customer choose exit");
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
