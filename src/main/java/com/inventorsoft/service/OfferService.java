package com.inventorsoft.service;

import com.inventorsoft.model.offer.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    List<Offer> getOffers();

    Offer saveOffer(final Offer offer);

    Optional<Offer> findByRoute(final String route);

    Optional<Offer> findById(final int id);

    boolean update(final int id, final Offer updates);

    boolean remove(final int id);
}
