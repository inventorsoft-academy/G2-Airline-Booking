package com.inventorsoft.validator;

import com.inventorsoft.model.offer.Offer;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfferValidator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    private String offersId = "";

    public boolean validateForUniqueOfferId(String offerId) {
        if (!offersId.contains(offerId)) {
            offersId += offerId + ",";
            return true;
        }
        return false;
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

    public boolean validateLocation(String location) {
        Pattern p = Pattern.compile("[a-zA-z]{4,}");
        Matcher m = p.matcher(location);
        return m.matches();
    }

    private boolean validateCityRoute(String route) {
        String[] array = route.split("->");
        return !array[0].equals(array[1]);
    }

    public boolean validateDate(String date) {
        Pattern p = Pattern.compile("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}");
        Matcher m = p.matcher(date);
        return m.matches() && compareToCurrentDate(date);
    }

    public boolean validateNumberOfSeats(String date) {
        Pattern p = Pattern.compile("[\\d,]+");
        Matcher m = p.matcher(date);
        return m.matches() ;
    }

    public boolean validatePrice(int price) {
        return price >= 80;
    }


    public boolean compareDepartureDateToArrivalDate(Date arrivalDate, Date departureDate) {
        return arrivalDate.compareTo(departureDate) > 0;
    }

    public boolean validateNumberOfSeats(int numberOfSeats) {
        return numberOfSeats >= 50;
    }


    public boolean compareToCurrentDate(String date) {
        boolean compare = false;
        try {
            compare = new Date().compareTo(DATE_FORMAT.parse(date)) <= 0;
        } catch (ParseException e) {
            System.out.println("wrong date format!");
        }
        return compare;
    }

    public boolean validateForExistence(int id, List<Offer> offerList) {
        for (Offer offer: offerList) {
            if (offer.getId() == id) {
                return true;
            }
        }
        return false;
    }


}
