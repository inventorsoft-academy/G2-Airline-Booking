package com.inventorsoft.downloadInfo;

import com.inventorsoft.model.ticket.Ticket;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DownloadTickets implements DownloadInfo {
    private static final String FILE_TICKETS = "src/main/resources/tickets.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    public static List<Ticket> getInfo() {
        List<Ticket> ticketList = new ArrayList<>();
        File file = new File(FILE_TICKETS);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                Ticket ticket = new Ticket();
                ticket.setName((s[0]));
                ticket.setCustomerId(Integer.parseInt(s[1]));
                ticket.setOfferId(Integer.parseInt(s[2]));
                ticket.setRoute((s[3]));
                try {
                    ticket.setDepartureDate(DATE_FORMAT.parse(s[4]));
                    ticket.setArrivalDate(DATE_FORMAT.parse(s[5]));
                } catch (ParseException e) {
                    System.out.println("Problem in DownloadTickets() ");
                    e.printStackTrace();
                }
                ticket.setNumber(s[6]);
                ticket.setPrice(Integer.parseInt(s[7]));
                ticketList.add(ticket);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketList;
    }
}
