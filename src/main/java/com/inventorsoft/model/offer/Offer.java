package com.inventorsoft.model.offer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Offer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private int offerId;
    private String route;
    private Date departure_time;
    private Date arrival_time;
    private String number_of_seats;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Date getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Date departure_time) {
        this.departure_time = departure_time;
    }

    public Date getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Date arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(String number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    @Override
    public String toString() {
        return offerId + " "
                + route + " "
                + DATE_FORMAT.format(departure_time) + " "
                + DATE_FORMAT.format(arrival_time) + " "
                + number_of_seats + " "
                + price + "\n";
    }
}
