package com.inventorsoft.model.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    private int id;

    private String departureCity;

    private String arrivalCity;

    private Date departureDate;

    private Date arrivalDate;

    private String numberOfSeats;

    private int price;

    @Override
    public String toString() {
        return id + " "
                + departureCity + " "
                + arrivalCity + " "
                + DATE_FORMAT.format(departureDate) + " "
                + DATE_FORMAT.format(arrivalDate) + " "
                + numberOfSeats + " "
                + price + "\n";
    }

    public void update(Offer offer) {
        this.id = offer.id;
        this.departureCity = offer.departureCity;
        this.arrivalCity = offer.arrivalCity;
        this.departureDate = offer.departureDate;
        this.arrivalDate = offer.arrivalDate;
        this.numberOfSeats = offer.numberOfSeats;
        this.price = offer.price;
    }

}
