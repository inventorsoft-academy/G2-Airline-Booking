package com.inventorsoft.model.ticket;/*
package com.Database.ticket;

import com.Database.offer.OfferHelper;
import com.IO.IdentifyPath;

import java.io.*;
import java.text.ParseException;

public class TicketHelper {
    private static final String FILE_TICKETS = "src/com/TextTest/tickets.txt";
    OfferHelper offerHelper = new OfferHelper();
    public static Ticket ticket = new Ticket();
    //download ticket from file
    void downloadTicketFromFile() {}

    private String answer;
    private String necessaryOfferId;
    public void start() throws IOException, ParseException {
        identifyOperation();
        implementPath();
    }

    private void implementPath() throws IOException, ParseException {
        switch (answer) {
            case "vtb":
                viewTicketBalance();
                break;
            case "st":
                searchTicket();
                break;
            case "bt":
                bookTicket();
                break;
            case "rt":
                reviewTickets();
                break;
            case "dt":
                downloadTicketFromFile();
                break;
        }
    }


    private void identifyOperation() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("view ticket balance, " +
                "search ticket, " +
                "book ticket, " +
                "review ticket, " +
                "download ticket? " +
                "(vtb,st,bt,rt,dt)");
        while (true) {
            answer = validNull(reader.readLine());
            if (answer.equals("vtb") ||
                    answer.equals("st") ||
                    answer.equals("bt") ||
                    answer.equals("rt") ||
                    answer.equals("dt")) break;
        }
    }

    private String validNull(String value){
        while (value.equals("")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                value = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    //for customer
    void viewTicketBalance() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("In which offer you want to see the tickets balance?(offerId)");
        necessaryOfferId = validNull(reader.readLine());
        offerHelper.ticketBlance(necessaryOfferId);
    }
    //search ticket by any value
    void searchTicket() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You want to search by?(offerId, route, departure_time, arrival_time)");
        necessaryOfferId = validNull(reader.readLine());
        switch (necessaryOfferId) {
            case "offerId":
                System.out.println("Enter offer Id:");
                offerHelper.searchTicketByValue(validNull(String.valueOf(reader.readLine())));
                break;
            case "route":
                offerHelper.searchTicketByValue(validNull(reader.readLine()));
                break;
            case "departure_time":
                offerHelper.searchTicketByValue(validNull(reader.readLine()));
                break;
            case "arrival_time":
                offerHelper.searchTicketByValue(validNull(reader.readLine()));
                break;
        }
    }

    //book ticket
    void bookTicket() throws IOException, ParseException {
        offerHelper.buyTicket();
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
            if (s[0].equals(IdentifyPath.customer.getName())) {
                System.out.println(line);
            }
        }
        br.close();
        fr.close();
    }



}
*/
