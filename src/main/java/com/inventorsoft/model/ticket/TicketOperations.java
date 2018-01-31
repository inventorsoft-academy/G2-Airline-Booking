
package com.inventorsoft.model.ticket;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.validator.OfferValidator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TicketOperations {

    private static final String FILE_TICKETS = "src/main/resources/tickets.txt";
    private static final String FILE_OFFERS = "src/main/resources/offers.txt";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private OfferValidator ov = new OfferValidator();
    private Ticket newTicket = new Ticket();
    private Offer newOffer;

    public void importAllOffers(List<Offer> offerList) {
        for (Offer offer : offerList) {
            System.out.println(showOffer(offer));
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
        newOffer = checkOfferById(offerList, id);
        while (newOffer == null) {
            System.out.println("Please input an existing id!");
            id = scn.nextInt();
            newOffer = checkOfferById(offerList, id);
        }
        //delete offer to change number of seats
        offerList.remove(newOffer);

        System.out.println("What ticket you want to buy?(number)");
        int number = scn.nextInt();

        //delete needs number of seats
        newOffer.setNumberOfSeats(deleteNeedsNumberOfSeats(newOffer, number));

        newTicket.setName(customer.getName());
        newTicket.setCustomerId(customer.getId());
        newTicket.setOfferId(newOffer.getId());
        newTicket.setRoute(newOffer.getRoute());
        newTicket.setDepartureDate(newOffer.getDepartureDate());
        newTicket.setArrivalDate(newOffer.getArrivalDate());
        newTicket.setNumber(number);
        newTicket.setPrice(newOffer.getPrice());

        offerList.add(newOffer);
        overwriteOffersInFile(offerList);
    }

    private Offer checkOfferById(List<Offer> offerList, int id) {
        for (Offer offer: offerList) {
            if (offer.getId() == id) {
                return offer;
            }
        }
        return null;
    }

    private Offer checkOfferByNumberOfSeat(List<Offer> offerList, int id) {
        for (Offer offer: offerList) {
            if (offer.getId() == id) {
                return offer;
            }
        }
        return null;
    }

    private String deleteNeedsNumberOfSeats(Offer offer, int number) {
        String numberOfSeats = "";
        String[] s = offer.getNumberOfSeats().split(",");
        for (int k = 0; k < s.length; k++) {

            boolean checker = true;
            //check if only one ticket
            if (s.length == 1 && Integer.parseInt(s[k]) == number) {
                checker = false;
            }
            //check needs ticket
            if (Integer.parseInt(s[k]) == number) {
                checker = false;
            }

            //check and write last ticket
            if (k+2 == s.length && Integer.parseInt(s[k+1]) == number) {
                numberOfSeats += s[k];
                break;
            }

            //correct write last ticket
            if (k+1 == s.length) {
                checker = false;
                numberOfSeats += s[k];
            }

            //write ticket
            if (checker) {
                numberOfSeats += s[k] + ",";
            }
        }
        System.out.println(numberOfSeats);
        return numberOfSeats;
    }

    //find offer by needs id
    private Offer findOfferById(List<Offer> offerList, int index) {
        for (Offer offer : offerList) {
            if (offer.getId() == index) {
                return offer;
            }
        }
        return null;
    }

    //only for create method, some comfort with input seats numbers 1,2,3,4,...50 (50)
    private String createNumberOfSeats(String number) {
        String numberOfSeats = "";
        for (int k = 1; k < Integer.parseInt(number); k++) {
            numberOfSeats += String.valueOf(k) + ",";
        }
        numberOfSeats += number;
        return numberOfSeats;
    }

    public void writeTicketInFile(Ticket ticket) throws IOException {
        FileWriter writer = new FileWriter(FILE_TICKETS, true);
        writer.append(ticket.toString());
        writer.flush();
        writer.close();
    }

    public void overwriteOffersInFile(List<Offer> offerList) throws IOException {
        FileWriter writer = new FileWriter(FILE_OFFERS, false);
        for (Offer offer : offerList) {
            writer.append(offer.toString());
        }
        writer.flush();
        writer.close();
    }

    //exit method
    public void exit() {
        System.out.println("Bye-bye, application will be closed");
        System.exit(0);
    }

    //balance of bought tickets
    public void balanceOfBoughtTickets(List<Ticket> ticketList) {
        int balance = 0;
        for (Ticket ticket: ticketList) {
            balance += ticket.getPrice();
        }
        System.out.println("Total balance: " + balance);
    }

    public List<Ticket> viewAllOrderedTickets(List<Ticket> ticketList, Customer customer) {
        List<Ticket> orderedTickets = new ArrayList<Ticket>();
        for (Ticket ticket: ticketList) {
            if (customer.getId() == ticket.getCustomerId()) {
                orderedTickets.add(ticket);
            }
        }
        return orderedTickets;
    }

    public void downloadTicket(List<Ticket> ticketList, Customer customer) {
        Scanner scn = new Scanner(System.in);
        List<Ticket> orderedTickets = viewAllOrderedTickets(ticketList,customer);

        if (!validateOrderedTicketsForNull(orderedTickets)) {
            System.out.println("What ticket you want to download?");
            for (Ticket ticket: orderedTickets) {
                System.out.println(showTicket(ticket));
            }

            System.out.println("Input id:");
            int id = scn.nextInt();
            while (!validateForExistOfferId(orderedTickets, id)) {
                System.out.println("There is no such id in your order list");
                id = scn.nextInt();
            }
            //if user get bought some tickets
            chooseFileNameToDownloadTicket(orderedTickets,customer,id);
        }
        else {
            System.out.println("You have not yet booked a ticket");
            //question
            //book
        }

    }

    private void chooseFileNameToDownloadTicket(List<Ticket> orderedTickets,Customer customer ,int id) {
        Scanner scn = new Scanner(System.in);
        Ticket orderedTicket = null;
        int answer = 0;
        for (Ticket ticket: orderedTickets) {
            if (ticket.getOfferId() == id) {
                orderedTicket = ticket;

                System.out.println("What file name do you want?" + "\n"
                        + "create my own file name - 1" + "\n"
                        + "create standard file name - 2");
                answer = scn.nextInt();
                while (!validateForEqualsInRange(answer, 2)) {
                    System.out.println("Please input 1 or 2!");
                    answer = scn.nextInt();
                }
            }
        }
        switch (answer) {
            case 1:
                System.out.println("Input file name (must be a - z, A - Z, 0 - 9):");
                String fileName = scn.next();
                while (!validateForStandardCharacterRange(fileName)) {
                    System.out.println("Please input symbols from range a - z, A - Z, 0 - 9!:");
                    fileName = scn.next();
                }
                try {
                    writeInFileDownloadedTicket(orderedTicket,customer,fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    writeInFileDownloadedTicket(orderedTicket,customer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    boolean validateForStandardCharacterRange(String s) {
        char[] charArray = s.toCharArray();
        for (char aCharArray : charArray) {
            int asciiCode = (int) aCharArray;
            if (asciiCode >= 65 && asciiCode <= 90) {
                return true;
            }
            if (asciiCode >= 97 && asciiCode <= 122) {
                return true;
            }
            if (asciiCode >= 48 && asciiCode <= 57) {
                return true;
            }
        }
        return false;
    }

    private void writeInFileDownloadedTicket(Ticket orderedTicket, Customer customer) throws IOException {
        String standardFileName = "src/main/resources/" + customer.getName() + orderedTicket.getOfferId() + ".txt";
        FileWriter writer = new FileWriter(standardFileName);
        writer.write(showTicket(orderedTicket));
        writer.flush();
        writer.close();
    }

    private void writeInFileDownloadedTicket(Ticket orderedTicket,Customer customer, String fileName) throws IOException {
        String specificFileName =  "src/main/resources/" + customer.getId() + fileName + ".txt";
        FileWriter writer = new FileWriter(specificFileName);
        writer.write(showTicket(orderedTicket));
        writer.flush();
        writer.close();
    }


    public boolean validateForEqualsInRange(int value, int number) {
        for (int k = 1; k <= number; k++) {
            if (k == value) {
                return true;
            }
        }
        return false;
    }

    boolean validateOrderedTicketsForNull (List<Ticket> ticketList) {
        return ticketList.isEmpty();
    }

    boolean validateForExistOfferId(List<Ticket> orderedTickets, int id) {
        for (Ticket ticket: orderedTickets) {
            if (ticket.getOfferId() == id) {
                return true;
            }
        }
        return false;
    }



    //search ticket by any value
    void searchTicketByNeedParameter() throws IOException, ParseException {
        Scanner scn = new Scanner(System.in);
        System.out.println("You want to search by?" + "\n"
                + "route - 1" + "\n"
                + "departure date - 2" + "\n"
                + "arrival date - 3 " + "\n"
                + "price - 4");
        int parameter = scn.nextInt();
        while (!validateForEqualsInRange(parameter,4)) {
            System.out.println("Input number from 1 to 4");
            parameter = scn.nextInt();
        }

        switch (parameter) {
            case 1:
                System.out.println("Enter offer route");
                //
                break;
            case 2:
                System.out.println("Enter offer departure date:");
                //
                break;
            case 3:
                System.out.println("Enter offer arrival date:");
                //
                break;
            case 4:
                System.out.println("Enter offer price:");
                //
                break;
        }
    }




}

