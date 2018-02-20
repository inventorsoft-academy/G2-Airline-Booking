package com.inventorsoft.service;


import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.repository.OfferInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultOfferService implements OfferService {

    private OfferInfoRepository offerInfoRepository;

    @Override
    public Optional<Offer> findById(int id) {
        return offerInfoRepository.findById(id);
    }

    @Override
    public List<Offer> getOffers() {
        return offerInfoRepository.getOffers();
    }

    @Override
    public Offer saveOffer(final Offer offer) {
        return offerInfoRepository.saveOffer(offer);
    }

    @Override
    public Optional<Offer> findByRoute(final String departureCity) {
        return offerInfoRepository.findByDepartureCity(departureCity);
    }

    @Override
    public boolean update(int id, Offer updateOffer) {
        return this.offerInfoRepository.updateOffer(id, updateOffer);
    }

    @Override
    public boolean remove(int id) {
        return this.offerInfoRepository.removeOffer(id);
    }

    @Override
    public Integer getTicketPrice() {
        return this.offerInfoRepository.getTicketPrice();
    }
}
