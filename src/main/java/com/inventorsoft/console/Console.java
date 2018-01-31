package com.inventorsoft.console;

import com.inventorsoft.downloadInfo.*;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Admin;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.session.AdminSession;
import com.inventorsoft.session.CustomerSession;
import com.inventorsoft.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Console {
    private static final View view = new View();
    private List<Offer> offerList = DownloadOffers.getInfo();
    private List<Ticket> ticketList = DownloadTickets.getInfo();

    public static void main(String[] args) throws IOException {


        view.welcome();
        Console console = new Console();
        console.start();

    }

    public void start() {
        Scanner scn = new Scanner(System.in);
        System.out.println("How do you want to work?" + "\n"
                + "like a customer - 1" + "\n"
                + "like a admin - 2");

        int answer = scn.nextInt();
        System.out.println(answer);
        while (!validateForEqualsInRange(answer, 2)) {
            System.out.println("Please input 1 or 2!");
            answer = scn.nextInt();
        }

        switch (answer) {
            case 1:
                List<Customer> customerList;
                customerList = DownloadCustomer.getInfo();
                try {
                    new CustomerSession(customerList, offerList, ticketList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                List<Admin> adminList;
                adminList = DownloadAdmin.getInfo();
                try {
                    new AdminSession(adminList, offerList, ticketList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public boolean validateForEqualsInRange(int value, int number) {
        for (int k = 1; k <= number; k++) {
            if (k == value) {
                return true;
            }
        }
        return false;
    }
}
