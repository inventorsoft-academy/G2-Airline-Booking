package com.inventorsoft.getInfoFromFile;

import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.validator.TicketValidator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GetTicketsFromFile implements GetInfoFromFile {
    private static final String FILE_TICKETS = "resources/tickets.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private final TicketValidator ticketValidator = new TicketValidator();

    public List<Ticket> getInfo() {
        List<Ticket> ticketList = new ArrayList<>();
        File file = new File(FILE_TICKETS);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (ticketValidator.validateTicketName(s[0])
                        && ticketValidator.validateCustomerId(s[1])
                        && ticketValidator.validateOfferId(s[2]) && ticketValidator.validateForUniqueTicket(s[1],s[2])
                        && ticketValidator.validateRoute(s[3])
                        && ticketValidator.validateDate(s[4])
                        && ticketValidator.validateDate(s[5])
                        && ticketValidator.validateNumberOfSeats(Integer.parseInt(s[6]))
                        && ticketValidator.validatePrice(Integer.parseInt(s[7]))) {
                    Ticket ticket = new Ticket();
                    ticket.setName((s[0]));
                    ticket.setCustomerId(Integer.parseInt(s[1]));
                    ticket.setOfferId(Integer.parseInt(s[2]));
                    ticket.setRoute((s[3]));
                    ticket.setNumber(Integer.parseInt(s[6]));
                    ticket.setPrice(Integer.parseInt(s[7]));
                    try {
                        ticket.setDepartureDate(DATE_FORMAT.parse(s[4]));
                        ticket.setArrivalDate(DATE_FORMAT.parse(s[5]));
                    } catch (ParseException e) {
                        System.out.println("Problem in GetTicketsFromFile() ");
                        e.printStackTrace();
                    }
                    ticketList.add(ticket);
                } else {
                    System.out.println("Problem in GetTicketFromFile");
                    System.exit(1);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketList;
    }
}
