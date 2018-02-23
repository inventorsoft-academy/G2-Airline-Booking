package com.inventorsoft.view;

import com.inventorsoft.controller.TicketsController;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.dao.SetModel;
import com.inventorsoft.validator.TicketValidator;
import com.inventorsoft.xml.WriteTicketInXML;

import javax.xml.transform.TransformerException;
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
                ", departureCity='" + offer.getDepartureCity() + '\'' +
                ", arrivalCity='" + offer.getArrivalCity() + '\'' +
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
                ", departureCity='" + ticket.getDepartureCity() + '\'' +
                ", arrivalCity='" + ticket.getArrivalCity() + '\'' +
                ", departure_time=" + ticket.getDepartureDate() +
                ", arrival_time=" + ticket.getArrivalDate() +
                ", number='" + ticket.getNumber() + '\'' +
                ", price=" + ticket.getPrice() +
                '}';
    }

    public void bookTicket(List<Offer> offerList, List<Ticket> ticketList, Customer customer) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("In which offer you are interested in?(id)");
        importAllOffers(offerList);

        int offerId = scn.nextInt();
        while (!ticketValidator.validateForExistenceOfferId(offerId, offerList)) {
            System.out.println("Please input an existing offer id!");
            offerId = scn.nextInt();
        }

        while (ticketValidator.validateForUniqueTicket(ticketList, customer, offerId)) {
            System.out.println("You already bought ticket for this plane, please choose other route!");
            importAllOffers(offerList);
            offerId = scn.nextInt();
        }

        System.out.println("What ticket you want to buy?(number)");
        int number = scn.nextInt();
        while (!ticketValidator.validateForExistenceNumberOfSeat(offerId, number, offerList)) {
            System.out.println("Please input an existing number of seat!");
            number = scn.nextInt();
        }

        ticketsController.bookTicket(offerId, number, offerList, customer);

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
            askCustomerToBuyATicket(offerList, ticketList, customer);
        }

    }

    private void askCustomerToBuyATicket(List<Offer> offerList, List<Ticket> ticketList, Customer customer) {
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
                bookTicket(offerList, ticketList, customer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseFileNameToDownloadTicket(List<Ticket> orderedTickets, Customer customer, int id) {
        System.out.println(customer);
        SetModel setModel = new SetModel();
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
                    String specificFileName = "src/main/resources/" + customer.getId() + fileName + ".xml";
                    try {
                        new WriteTicketInXML(orderedTicket,specificFileName);
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    String standardFileName = "src/main/resources/" + customer.getName() + orderedTicket.getOfferId() + ".xml";
                    try {
                        new WriteTicketInXML(orderedTicket,standardFileName);
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
