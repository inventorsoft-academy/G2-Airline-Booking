package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferInfoRepository {

    List<Offer> getOffers();

    Offer saveOffer(final Offer offer);

    Optional<Offer> findByDepartureCity(final String departureCity);

    Optional<Offer> findById(final int id);

    boolean updateOffer(final int id, final Offer newOffer);

    boolean removeOffer(final int id);

    Integer getTicketPrice();
}
