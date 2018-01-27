/*
package com.inventorsoft.model.ticket;

import com.Database.offer.OfferOperations;
import com.IO.Validator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.Database.offer.OfferOperations.offer;

public class TicketOperations {

    private static final String FILE_OFFERS = "src/com/TextTest/offers.txt";
    private static final String FILE_TICKETS = "src/com/TextTest/tickets.txt";
    private static final String FILE_BUFFER_OFFERS = "src/com/TextTest/bufferoffers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private static final Validator validator = new Validator();
    private static final OfferOperations offerOperations = new OfferOperations();
    private String necessaryOfferId;
    public static Ticket ticket = new Ticket();

    //for customer
    void viewTicketBalance() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("In which offer you want to see the tickets balance?(offerId)");
        necessaryOfferId = validator.validNull(reader.readLine());
        ticketBalance(necessaryOfferId);
    }

    //search ticket by any value
    void searchTicket() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You want to search by?(id, route, departure time, arrival time)");
        necessaryOfferId = validator.validNull(reader.readLine());
        switch (necessaryOfferId) {
            case "id":
                System.out.println("Enter offer id:");
                searchTicketByValue(validator.validNull(String.valueOf(reader.readLine())));
                break;
            case "route":
                System.out.println("Enter offer route:");
                searchTicketByValue(validator.validNull(reader.readLine()));
                break;
            case "departure time":
                System.out.println("Enter offer departure time:");
                searchTicketByValue(validator.validNull(reader.readLine()));
                break;
            case "arrival time":
                System.out.println("Enter offer arrival time:");
                searchTicketByValue(validator.validNull(reader.readLine()));
                break;
        }
    }

    //book ticket
    void bookTicket() throws IOException, ParseException {
        buyTicket();
        FileWriter writer = new FileWriter(FILE_TICKETS, true);
        writer.append(ticket.toString());
        writer.flush();
        writer.close();
    }

    //review ticket
    void reviewTickets() throws IOException {
        File file = new File(FILE_TICKETS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (s[0].equals(com.IO.IdentifyPath.customer.getName())) {
                System.out.println(line);
            }
        }
        br.close();
        fr.close();
    }

    private void ticketBalance(String necessaryOfferId) throws IOException, ParseException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (s[0].equals(necessaryOfferId)
                    || s[1].equals(necessaryOfferId)
                    || s[2].equals(necessaryOfferId)
                    || s[3].equals(necessaryOfferId)
                    || s[4].equals(necessaryOfferId)
                    || s[5].equals(necessaryOfferId)) {
                offer.setOfferId(Integer.parseInt(s[0]));
                offer.setRoute(validator.validNull(s[1]));
                offer.setDeparture_time(DATE_FORMAT.parse(s[2]));
                offer.setArrival_time(DATE_FORMAT.parse(s[3]));
                offer.setNumber_of_seats(validator.validNull(s[4]));
                offer.setPrice(Integer.parseInt(s[5]));
            }
        }
        br.close();
        fr.close();
        System.out.println(offer.getNumber_of_seats());

    }

    private void searchTicketByValue(String necessaryValue) throws IOException, ParseException {
        System.out.println("Available number of seats:");
        ticketBalance(necessaryValue);
    }

    private void buyTicket() throws IOException, ParseException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        fr.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you are interested in?(offerId)");
        String necessaryId = reader.readLine();
        ticketBalance(validator.validNull(necessaryId));

        System.out.println("What ticket you want to buy?(number)");
        String number = validator.validNull(reader.readLine());

        String numberOfSeats = "";
        String[] s = offer.getNumber_of_seats().split(",");
        for (int k = 0; k < s.length; k++) {
            boolean checker = true;
            //check if only one ticket
            if (s.length == 1) {
                checker = false;
            }
            //check needs ticket
            if (s[k].equals(number)) {
                checker = false;
            }
            //check and write last ticket
            if (k+2 == s.length && s[k+1].equals(number)) {
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
        offer.setNumber_of_seats(numberOfSeats);

        file = new File(FILE_OFFERS);
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        while ((line = br.readLine()) != null) {
            s = line.split(" ");
            if (s[0].equals(necessaryId)) {
                OfferOperations.writeOffer(offer);
                continue;
            }
            offerOperations.writeOffer(line);
        }

        ticket.setName(com.IO.IdentifyPath.customer.getName());
        ticket.setCustomerId(com.IO.IdentifyPath.customer.getId());
        ticket.setOfferId(offer.getOfferId());
        ticket.setRoute(offer.getRoute());
        ticket.setDeparture_time(offer.getDeparture_time());
        ticket.setArrival_time(offer.getArrival_time());
        ticket.setNumber(number);
        ticket.setPrice(offer.getPrice());
        br.close();
        fr.close();
        offerOperations.writeFromBuffer();
        offerOperations.cleanFile(FILE_BUFFER_OFFERS);
    }

    void downloadTicketToFile() {
        //NOP

    }





}
*/
