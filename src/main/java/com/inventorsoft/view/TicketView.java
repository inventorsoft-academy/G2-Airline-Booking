package com.inventorsoft.view;

import com.inventorsoft.controller.TicketsController;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.setInfoToFile.SetModelToFile;
import com.inventorsoft.validator.TicketValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TicketView {

    TicketValidator ticketValidator = new TicketValidator();
    TicketsController ticketsController = new TicketsController();

    public void importAllOffers(List<Offer> offerList) {
        for (Offer offer : offerList) {
            System.out.println(showOffer(offer));
        }
    }

    public void importAllTickets(List<Ticket> ticketList) {
        for (Ticket ticket : ticketList) {
            System.out.println(showTicket(ticket));
        }
    }

    private String showOffer(Offer offer) {
        return "Offer{" +
                "id=" + offer.getId() +
                ", route='" + offer.getRoute() + '\'' +
                ", departureDate=" + offer.getDepartureDate() +
                ", arrivalDate=" + offer.getArrivalDate() +
                ", numberOfSeats='" + offer.getNumberOfSeats() + '\'' +
                ", price=" + offer.getPrice() +
                '}';
    }

    private String showTicket(Ticket ticket) {
        return "Ticket{" +
                "name='" + ticket.getName() + '\'' +
                ", id=" + ticket.getOfferId() +
                ", route='" + ticket.getRoute() + '\'' +
                ", departure_time=" + ticket.getDepartureDate() +
                ", arrival_time=" + ticket.getArrivalDate() +
                ", number='" + ticket.getNumber() + '\'' +
                ", price=" + ticket.getPrice() +
                '}';
    }

    public void bookTicket(List<Offer> offerList, Customer customer) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("In which offer you are interested in?(id)");
        importAllOffers(offerList);

        int id = scn.nextInt();
        while (!ticketValidator.validateForExistenceOfferId(id, offerList)) {
            System.out.println("Please input an existing id!");
            id = scn.nextInt();
        }

        System.out.println("What ticket you want to buy?(number)");
        int number = scn.nextInt();
        while (!ticketValidator.validateForExistenceNumberOfSeat(id, number, offerList)) {
            System.out.println("Please input an existing number of seat!");
            number = scn.nextInt();
        }

        ticketsController.bookTicket(id, number, offerList, customer);

    }


    //balance of bought tickets
    public void balanceOfBoughtTickets(List<Ticket> ticketList) {
        System.out.println("Total balance: " + ticketsController.balanceOfBoughtTickets(ticketList) + "$");
    }

    public void viewAllOrderedTickets(List<Ticket> ticketList, Customer customer) {
        importAllTickets(ticketsController.viewAllOrderedTickets(ticketList, customer));
    }


    public void downloadTicket(List<Offer> offerList, List<Ticket> ticketList, Customer customer) {
        Scanner scn = new Scanner(System.in);
        List<Ticket> orderedTickets = ticketsController.viewAllOrderedTickets(ticketList, customer);

        if (!ticketValidator.isEmptyTicketList(orderedTickets)) {

            System.out.println("What ticket you want to download?");
            for (Ticket ticket : orderedTickets) {
                System.out.println(showTicket(ticket));
            }

            System.out.println("Input id:");
            int id = scn.nextInt();
            while (!ticketValidator.validateForExistenceOfferIdInTicket(id, ticketList)) {
                System.out.println("There is no such id in your order list");
                id = scn.nextInt();
            }

            //if user get bought some tickets
            chooseFileNameToDownloadTicket(orderedTickets, customer, id);

        } else {
            askCustomerToBuyATicket(offerList,customer);
        }

    }

    private void askCustomerToBuyATicket(List<Offer> offerList, Customer customer) {
        Scanner scn = new Scanner(System.in);
        System.out.println("You have not book a ticket yet" + "\n"
        + "Maybe you want to book a ticket" + "\n"
        + "yes - 1" + "\n" + "no - 2");

        int answer = scn.nextInt();
        System.out.println(answer);
        while (!ticketValidator.validateForEqualsInRange(answer, 2)) {
            System.out.println("Please input 1 or 2!");
            answer = scn.nextInt();
        }
        if (answer == 1) {
            try {
                bookTicket(offerList, customer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseFileNameToDownloadTicket(List<Ticket> orderedTickets, Customer customer, int id) {
        System.out.println(customer);
        SetModelToFile setModelToFile = new SetModelToFile();
        Scanner scn = new Scanner(System.in);
        Ticket orderedTicket = null;
        int answer = 0;
        for (Ticket ticket : orderedTickets) {
            if (ticket.getOfferId() == id) {
                orderedTicket = ticket;

                System.out.println("What file name do you want?" + "\n"
                        + "create my own file name - 1" + "\n"
                        + "create standard file name - 2");
                answer = scn.nextInt();
                while (!ticketValidator.validateForEqualsInRange(answer, 2)) {
                    System.out.println("Please input 1 or 2!");
                    answer = scn.nextInt();
                }
            }
        }
        switch (answer) {
            case 1:
                System.out.println("Input file name (must be a - z, A - Z, 0 - 9):");
                String fileName = scn.next();
                while (!ticketValidator.validateFileName(fileName)) {
                    System.out.println("Please input symbols from range a - z, A - Z, 0 - 9!:");
                    fileName = scn.next();
                }
                try {
                    String specificFileName = "resources/" + customer.getId() + fileName + ".xml";
                    setModelToFile.setInfo(showTicket(orderedTicket), specificFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    String standardFileName = "resources/" + customer.getName() + orderedTicket.getOfferId() + ".xml";
                    setModelToFile.setInfo(showTicket(orderedTicket), standardFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
