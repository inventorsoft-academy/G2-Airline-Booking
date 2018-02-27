package com.inventorsoft.model.offer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OFFERS")
public class Offer {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "DEPARTURECITY", nullable = false)
    private String departureCity;

    @Column(name = "ARRIVALCITY", nullable = false)
    private String arrivalCity;

    @JsonFormat(pattern = "dd/MM/yyyy-HH:mm")
    @Column(name = "DEPARTUREDATE", nullable = false)
    private Date departureDate;
    @JsonFormat(pattern = "dd/MM/yyyy-HH:mm")
    @Column(name = "ARRIVALDATE", nullable = false)
    private Date arrivalDate;

    @Column(name = "NUMBEROFSEATS", nullable = false)
    private String numberOfSeats;

    @Column(name = "PRICE", nullable = false)
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
        this.departureCity = offer.departureCity;
        this.arrivalCity = offer.arrivalCity;
        this.departureDate = offer.departureDate;
        this.arrivalDate = offer.arrivalDate;
        this.numberOfSeats = offer.numberOfSeats;
        this.price = offer.price;
    }

}
