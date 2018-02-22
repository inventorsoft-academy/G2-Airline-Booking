package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.validator.TicketValidator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository implements TicketInfoRepository {

    private static final String FILE_TICKETS = "src/main/resources/tickets.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private final TicketValidator ticketValidator = new TicketValidator();
    private List<Ticket> ticketList;

    @PostConstruct
    public void start() {
        ticketList = getInfo();
        System.out.println(ticketList);
    }

    @PreDestroy
    public void finish() throws IOException {
        FileWriter writer = new FileWriter(FILE_TICKETS, false);
        for (Ticket ticket : ticketList) {
            writer.append(ticket.toString());
        }
        writer.flush();
        writer.close();
    }

    public List<Ticket> getInfo() {
        List<Ticket> ticketList = new ArrayList<>();
        File file = new File(FILE_TICKETS);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (ticketValidator.validateForAllValues(s)) {
                    Ticket ticket = new Ticket();
                    ticket.setName((s[0]));
                    ticket.setCustomerId(Integer.parseInt(s[1]));
                    ticket.setOfferId(Integer.parseInt(s[2]));
                    ticket.setDepartureCity((s[3]));
                    ticket.setArrivalCity(s[4]);
                    ticket.setNumber(Integer.parseInt(s[7]));
                    ticket.setPrice(Integer.parseInt(s[8]));
                    try {
                        ticket.setDepartureDate(DATE_FORMAT.parse(s[5]));
                        ticket.setArrivalDate(DATE_FORMAT.parse(s[6]));
                    } catch (ParseException e) {
                        System.out.println("Problem in GetTickets() ");
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


    @Override
    public Integer getTicketPrice() {
        return ticketList.stream().mapToInt(Ticket::getPrice).sum();
    }
}
