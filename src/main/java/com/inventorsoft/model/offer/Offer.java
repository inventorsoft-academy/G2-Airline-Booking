package com.inventorsoft.model.offer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Offer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private int id;
    private String route;
    private Date departureDate;
    private Date arrivalDate;
    private String numberOfSeats;
    private int price;

    public static SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " "
                + route + " "
                + DATE_FORMAT.format(departureDate) + " "
                + DATE_FORMAT.format(arrivalDate) + " "
                + numberOfSeats + " "
                + price + "\n";
    }
}
