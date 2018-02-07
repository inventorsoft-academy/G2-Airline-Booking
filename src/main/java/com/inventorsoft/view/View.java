package com.inventorsoft.view;


import com.inventorsoft.getInfoFromFile.GetAdminsFromFile;
import com.inventorsoft.getInfoFromFile.GetCustomersFromFile;
import com.inventorsoft.getInfoFromFile.GetOffersFromFile;
import com.inventorsoft.getInfoFromFile.GetTicketsFromFile;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Admin;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.validator.ViewValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import static com.inventorsoft.console.Console.logger;

public class View {
    private ViewValidator vw = new ViewValidator();
    private GetOffersFromFile getOffersFromFile = new GetOffersFromFile();
    private GetTicketsFromFile getTicketsFromFile = new GetTicketsFromFile();
    private GetAdminsFromFile getAdminsFromFile = new GetAdminsFromFile();
    private GetCustomersFromFile getCustomersFromFile = new GetCustomersFromFile();

    public void welcome() {
        System.out.println("=====" + "Welcome to G2-Airline-Booking!" + "=====");
        delimiter();
    }

    public void start() {
        welcome();
        logger.info("Start to get offers and tickets from file");
        List<Offer> offerList = getOffersFromFile.getInfo();
        List<Ticket> ticketList = getTicketsFromFile.getInfo();
        logger.info("Correct work in get offers and tickets from file");

        Scanner scn = new Scanner(System.in);
        System.out.println("How do you want to work?" + "\n"
                + "like a customer - 1" + "\n"
                + "like a admin - 2");

        int answer = scn.nextInt();
        System.out.println(answer);
        while (!vw.validateForEqualsInRange(answer, 2)) {
            System.out.println("Please input 1 or 2!");
            answer = scn.nextInt();
        }

        switch (answer) {
            case 1:
                try {
                    logger.info("User choose - 1(customer)");
                    List<Customer> customerList;
                    customerList = getCustomersFromFile.getInfo();
                    new CustomerView(customerList, offerList, ticketList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                logger.info("User choose - 2(admin)");
                List<Admin> adminList;
                adminList = getAdminsFromFile.getInfo();
                try {
                    new AdminView(adminList, offerList, ticketList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void delimiter() {
        System.out.println("========================================");
    }

}
