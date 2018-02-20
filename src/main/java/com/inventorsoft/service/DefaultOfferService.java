package com.inventorsoft.service;


import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.repository.OfferInfoRepository;
import com.inventorsoft.repository.OfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultOfferService implements OfferService {

    private OfferInfoRepository offerRepository;

    @Override
    public Optional<Offer> findById(int id) {
        return offerRepository.findById(id);
    }

    @Override
    public List<Offer> getOffers() {
        return offerRepository.getOffers();
    }

    @Override
    public Offer saveOffer(final Offer offer) {
        return offerRepository.saveOffer(offer);
    }

    @Override
    public Optional<Offer> findByRoute(final String departureCity) {
        return offerRepository.findByDepartureCity(departureCity);
    }

    @Override
    public boolean update(int id, Offer updateOffer) {
        return this.offerRepository.updateOffer(id, updateOffer);
    }

    @Override
    public boolean remove(int id) {
        return this.offerRepository.removeOffer(id);
    }
}
