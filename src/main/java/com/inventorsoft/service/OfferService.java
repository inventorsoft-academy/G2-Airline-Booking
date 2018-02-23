package com.inventorsoft.service;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OfferService {

    List<Offer> getOffers();

    List<Offer> getOffersForCustomer();

    List<Offer> searchOffers(final String departureCity, String departureDate);

    Offer saveOffer(final Offer offer);

    Optional<Offer> findByRoute(final String route);

    Optional<Offer> findById(final int id);

    boolean update(final int id, final Offer updates);

    boolean remove(final int id);

    Ticket bookATicket(String offerId, String customerId, String numberOfSeat);
}
