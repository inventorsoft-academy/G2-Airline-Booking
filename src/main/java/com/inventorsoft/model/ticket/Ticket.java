package com.inventorsoft.model.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    private String name;

    private int customerId;

    private int offerId;

    private String route;

    private Date departureDate;

    private Date arrivalDate;

    private int number;

    private int price;

    @Override
    public String toString() {
        return name + " "
                + customerId + " "
                + offerId + " "
                + route + " "
                + DATE_FORMAT.format(departureDate) + " "
                + DATE_FORMAT.format(arrivalDate) + " "
                + number + " "
                + price + "\n";
    }

    public void update(Ticket ticket) {
        this.name = ticket.name;
        this.customerId = ticket.customerId;
        this.offerId = ticket.offerId;
        this.route = ticket.route;
        this.departureDate = ticket.departureDate;
        this.arrivalDate = ticket.arrivalDate;
        this.number = ticket.number;
        this.price = ticket.price;
    }

}