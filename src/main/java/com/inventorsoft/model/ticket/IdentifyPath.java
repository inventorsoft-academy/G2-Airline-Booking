/*
package com.inventorsoft.model.ticket;

import com.IO.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class IdentifyPath {


    private String answer;
    private static final Validator validator = new Validator();
    private static final TicketOperations ticketOperation = new TicketOperations();

    public IdentifyPath() throws IOException, ParseException {
        identifyOperation();
        implementPath();
    }

    private void implementPath() throws IOException, ParseException {
        switch (answer) {
            case "vtb":
                ticketOperation.viewTicketBalance();
                break;
            case "st":
                ticketOperation.searchTicket();
                break;
            case "bt":
                ticketOperation.bookTicket();
                break;
            case "rt":
                ticketOperation.reviewTickets();
                break;
            case "dt":
                ticketOperation.downloadTicketToFile();
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
            answer = validator.validNull(reader.readLine());
            if (answer.equals("vtb") ||
                    answer.equals("st") ||
                    answer.equals("bt") ||
                    answer.equals("rt") ||
                    answer.equals("dt")) break;
        }
    }

}
*/
