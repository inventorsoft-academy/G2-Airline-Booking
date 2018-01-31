package com.inventorsoft.validator;
import com.inventorsoft.model.offer.Offer;


import java.util.Date;
import java.util.List;

public class OfferValidator {

    public int autoIncrementId(final List<Offer> offerList) {
        int maxId = 0;
        for (Offer offer: offerList) {
            if (maxId < offer.getId()) {
                maxId = offer.getId();
            }

        }
        return ++maxId;
    }

    public boolean validateRoute(String route) {
        String[] cityFromCityTO = route.split("->",2);
        return route.contains("->") && cityFromCityTO[0].length() >= 6 && cityFromCityTO[1].length() >= 6;
    }

    public boolean compareDepartureDateToArrivalDate(Date arrivalDate, Date departureDate) {
        return arrivalDate.compareTo(departureDate) > 0;
    }

    public boolean validateNumberOfSeats(String numberOfSeats) {
        return numberOfSeats.split(",").length >= 50;
    }

    public boolean validatePrice(int price) {
        return price >= 80;
    }

    public boolean compareToCurrentDate(Date date) {
        return new Date().compareTo(date) <= 0;
    }

    public boolean validateForOneOrTwo(String value) {
        return value.equals("1") || value.equals("2");
    }

    public boolean validateForNull(int number) {
        return number >= 0;
    }

    public boolean validateForEqualsInRange(int value, int number) {
        for (int k = 1; k <= number; k++) {
            if (k == value) {
                return true;
            }
        }
        return false;
    }

    public boolean validateDate(String s) {
        char[] charArray = s.toCharArray();
        if (s.length() < 16 ) {
            return false;
        }
        return  (int)charArray[0] <= 57 && (int)charArray[0] >= 48 &&
                (int)charArray[1] <= 57 && (int)charArray[1] >= 48 &&
                (int)charArray[2] == 47 &&
                (int)charArray[3] <= 57 && (int)charArray[3] >= 48 &&
                (int)charArray[4] <= 57 && (int)charArray[4] >= 48 &&
                (int)charArray[5] == 47 &&
                (int)charArray[6] <= 57 && (int)charArray[6] >= 48 &&
                (int)charArray[7] <= 57 && (int)charArray[7] >= 48 &&
                (int)charArray[8] <= 57 && (int)charArray[8] >= 48 &&
                (int)charArray[9] <= 57 && (int)charArray[9] >= 48 &&
                (int)charArray[10] == 45 &&
                (int)charArray[11] <= 57 && (int)charArray[11] >= 48 &&
                (int)charArray[12] <= 57 && (int)charArray[12] >= 48 &&
                (int)charArray[13] == 58 &&
                (int)charArray[14] <= 57 && (int)charArray[14] >= 48 &&
                (int)charArray[15] <= 57 && (int)charArray[15] >= 48;
    }

}
