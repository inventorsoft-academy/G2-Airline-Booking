package com.inventorsoft.validator;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketValidator {

    public boolean validateForAllValues(String[] s) {
        return validateTicketName(s[0])
                && validateCustomerId(s[1])
                && validateOfferId(s[2])
                && validateForUniqueTicket(s[1], s[2])
                && validateRoute(s[3])
                && validateDate(s[4])
                && validateDate(s[5])
                && validateNumberOfSeats(Integer.parseInt(s[6]))
                && validatePrice(Integer.parseInt(s[7]));
    }

    public boolean validateForUniqueTicket(List<Ticket> ticketList, Customer customer, int offerId) {
        for (Ticket ticket: ticketList) {
            concatId += String.valueOf(ticket.getCustomerId()) + String.valueOf(ticket.getOfferId()) + ",";
        }
        return concatId.contains(String.valueOf(customer.getId()) + String.valueOf(offerId));
    }

    private String concatId = "";

    public boolean validateForUniqueTicket(String customerId, String offerId) {
        if (!concatId.contains(customerId + offerId)) {
            concatId += customerId + offerId + ",";
            return true;
        }
        return false;
    }

    public boolean validateTicketName(String name) {
        Pattern p = Pattern.compile("[a-zA-z]{2,40}");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public boolean validateCustomerId(String offerId) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(offerId);
        return m.matches();
    }

    public boolean validateOfferId(String offerId) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(offerId);
        return m.matches();
    }

    public boolean validateRoute(String route) {
        Pattern p = Pattern.compile("[a-zA-z]{4,}->[a-zA-z]{4,}");
        Matcher m = p.matcher(route);
        return m.matches() && validateCityRoute(route);
    }

    private boolean validateCityRoute(String route) {
        String[] array = route.split("->");
        return !array[0].equals(array[1]);
    }

    public boolean validateDate(String date) {
        Pattern p = Pattern.compile("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}");
        Matcher m = p.matcher(date);
        return m.matches();
    }

    public boolean validateFileName(String fileName) {
        Pattern p = Pattern.compile("\\w{6,40}");
        Matcher m = p.matcher(fileName);
        return m.matches();
    }


    public boolean validateNumberOfSeats(int number) {
        return number > 0;
    }

    public boolean validatePrice(int price) {
        return price >= 80;
    }

    public boolean validateForExistenceOfferId(int id, List<Offer> offerList) {
        for (Offer offer : offerList) {
            if (offer.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean validateForExistenceOfferIdInTicket(int id, List<Ticket> orderedTickets) {
        for (Ticket ticket : orderedTickets) {
            if (ticket.getOfferId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean validateForExistenceNumberOfSeat(int id, int number, List<Offer> offerList) {
        for (Offer offer : offerList) {
            if (offer.getNumberOfSeats().contains(String.valueOf(number))
                    && offer.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyTicketList(List<Ticket> orderedTickets) {
        return orderedTickets.isEmpty();
    }

    public boolean validateForEqualsInRange(int value, int number) {
        return value >= 1 && value <= number;
    }

    public boolean validateForEqualsInRange(String value, String number) {
        return value.compareTo("1") >= 0 && value.compareTo(number) <= 0;
    }


    public boolean compareToCurrentDate(String date) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
        boolean compare = false;
        try {
            compare = new Date().compareTo(DATE_FORMAT.parse(date)) <= 0;
        } catch (ParseException e) {
            System.out.println("wrong date format!");
        }
        return compare;
    }


}
