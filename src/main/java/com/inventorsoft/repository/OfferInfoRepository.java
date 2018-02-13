package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferInfoRepository {

    List<Offer> findAll();

    Offer save(final Offer offer);

    Optional<Offer> findByRoute(final String route);

    Optional<Offer> findById(final int id);

    boolean update(final int id, final Offer newOffer);

    boolean remove(final int id);

}
