package com.inventorsoft.controller;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.dao.SetModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketsController {

    private SetModel setModel = new SetModel();

    public void bookTicket(int id, int number, List<Offer> offerList, Customer customer) throws IOException {

        Offer offer = findOfferById(offerList, id);

        //delete offer to change number of seats
        offerList.remove(offer);

        //delete needs number of seats
        offer.setNumberOfSeats(deleteNeedsNumberOfSeats(offer, number));
        Ticket ticket = new Ticket();
        ticket.setName(customer.getName());
        ticket.setCustomerId(customer.getId());
        ticket.setOfferId(offer.getId());
        ticket.setDepartureCity(offer.getDepartureCity());
        ticket.setArrivalCity(offer.getArrivalCity());
        ticket.setDepartureDate(offer.getDepartureDate());
        ticket.setArrivalDate(offer.getArrivalDate());
        ticket.setNumber(number);
        ticket.setPrice(offer.getPrice());

        //changed offer
        //check if customers bought all tickets
        if (!ifBoughtAllTheTickets(offer)) {
            offerList.add(offer);
        }


        setModel.setInfo(offerList, "resources/offers.txt");

        setModel.setInfo(ticket, "resources/tickets.txt");

    }

    private Offer findOfferById(List<Offer> offerList, int id) {
        for (Offer offer : offerList) {
            if (offer.getId() == id) {
                return offer;
            }
        }
        return null;
    }

    private boolean ifBoughtAllTheTickets(Offer offer) {
        return offer.getNumberOfSeats().equals("");
    }

    private String deleteNeedsNumberOfSeats(Offer offer, int number) {
        String numberOfSeats = "";
        String[] s = offer.getNumberOfSeats().split(",");
        for (int k = 0; k < s.length; k++) {

            boolean checker = true;

            //check if customer buy last ticket
            if (s.length == 1 && Integer.parseInt(s[k]) == number) {
                break;
            }

            //check needs ticket
            if (Integer.parseInt(s[k]) == number) {
                checker = false;
            }

            //check and write last ticket without if customer buy it ','
            if (k + 2 == s.length && Integer.parseInt(s[k + 1]) == number) {
                numberOfSeats += s[k];
                break;
            }

            //correct write last ticket
            if (k + 1 == s.length) {
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

    public void exit() {
        System.exit(0);
    }

    public int balanceOfBoughtTickets(List<Ticket> ticketList) {
        int balance = 0;
        for (Ticket ticket : ticketList) {
            balance += ticket.getPrice();
        }
        return balance;
    }

    public List<Ticket> viewAllOrderedTickets(List<Ticket> ticketList, Customer customer) {
        List<Ticket> orderedTickets = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            if (customer.getId() == ticket.getCustomerId()) {
                orderedTickets.add(ticket);
            }
        }
        return orderedTickets;
    }

}
