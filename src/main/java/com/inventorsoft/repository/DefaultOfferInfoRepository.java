/*
package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class DefaultOfferInfoRepository implements OfferInfoRepository {


    private static final List<Offer> storage;

    static {
        storage = new ArrayList<>();
        storage.add(new Offer(1, "ЗПЖ", "ЗФЖ", new Date(), new Date(), "13,21,42,63,94", 90));
        storage.add(new Offer(2, "ВДН", "УЛК", new Date(), new Date(), "1,2,3", 250));
    }


    @Override
    public List<Offer> getOffers() {
        return storage;
    }

    @Override
    public Offer saveOffer(Offer offer) {
        return null;
    }

    @Override
    public Optional<Offer> findByDepartureCity(String departureCity) {
        return null;
    }

    @Override
    public Optional<Offer> findById(int id) {
        return storage.stream().filter(offer -> offer.getId() == id).findAny();
    }

    @Override
    public boolean updateOffer(int id, Offer newOffer) {
        return false;
    }

    @Override
    public boolean removeOffer(int id) {
        return false;
    }

    @Override
    public Integer getTicketPrice() {
        return null;
    }

}
*/
