package com.inventorsoft.validator;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.xml.ReadXMLFileDOMExample;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfferValidator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    private static final ReadXMLFileDOMExample xml = new ReadXMLFileDOMExample();

    private String offersId = "";

    public boolean validateForAllValues(String[] s) {
        return validateOfferId(s[0]) && validateForUniqueOfferId(s[0])
                && validateCityKey(s[1])
                && validateCityKey(s[2])
                && validateDate(s[3]) && compareToCurrentDate(s[3])
                && validateDate(s[4])
                && validateNumberOfSeats(s[5])
                && validatePrice(Integer.parseInt(s[6]));
    }
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

    public boolean validateCityKey(String cityKey) {
        Map<String, String> cities = xml.getCities();
        for (Map.Entry<String, String> entry : cities.entrySet()) {
            if (entry.getKey().equals(cityKey)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateLocation(String location) {
        Map<String, String> cities = xml.getCities();
        for (Map.Entry<String, String> entry : cities.entrySet()) {
            if (entry.getValue().equals(location)) {
                return true;
            }
        }
        return false;
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

    public boolean validateNumberOfSeats(String numberOfSeats) {
        Pattern p = Pattern.compile("[\\d,]+");
        Matcher m = p.matcher(numberOfSeats);
        return m.matches();
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
        for (Offer offer : offerList) {
            if (offer.getId() == id) {
                return true;
            }
        }
        return false;
    }


}
