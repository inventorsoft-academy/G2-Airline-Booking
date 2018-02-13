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
        return offerInfoRepository.findAll();
    }

    @Override
    public Offer saveOffer(final Offer offer) {
        return offerInfoRepository.save(offer);
    }

    @Override
    public Optional<Offer> findByRoute(final String route) {
        return offerInfoRepository.findByRoute(route);
    }

    @Override
    public boolean update(int id, Offer updateOffer) {
        return this.offerInfoRepository.update(id, updateOffer);
    }

    @Override
    public boolean remove(int id) {
        return this.offerInfoRepository.remove(id);
    }
}
