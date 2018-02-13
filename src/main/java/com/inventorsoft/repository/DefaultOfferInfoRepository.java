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
        storage.add(new Offer(1, "Canada->Ukraine", new Date(), new Date(), "13,21,42,63,94", 90));
        storage.add(new Offer(2, "Amsterdam->Ukraine", new Date(), new Date(), "1,2,3", 250));
    }

    @Override
    public List<Offer> findAll() {
        return storage;
    }

    @Override
    public Offer save(Offer offer) {
        offer.setId(storage.size());
        storage.add(offer);
        return offer;
    }

    @Override
    public Optional<Offer> findByRoute(String route) {
        return storage.stream().filter(offer -> offer.getRoute().equals(route)).findAny();
    }

    @Override
    public Optional<Offer> findById(int id) {
        return storage.stream().filter(offer -> offer.getId() == id).findAny();
    }

    @Override
    public boolean update(int id, Offer newOffer) {
        final Optional<Offer> matchOfferOptional = findById(id);
        matchOfferOptional.ifPresent(offer -> offer.update(newOffer));
        return matchOfferOptional.isPresent();
    }

    @Override
    public boolean remove(int id) {
        return storage.removeIf(offer -> offer.getId() == id);
    }
}
