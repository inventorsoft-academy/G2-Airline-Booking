package com.inventorsoft.service;

import com.inventorsoft.dao.OffersDao;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.repository.OfferInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultOfferService implements OfferService {

    private OffersDao offersDao;

    @Override
    public Optional<Offer> findById(int id) {
        return offersDao.findById(id);
    }

    @Override
    public List<Offer> getOffers() {
        return offersDao.getOffers();
    }

    @Override
    public Offer saveOffer(final Offer offer) {
        return offersDao.saveOffer(offer);
    }

    @Override
    public Optional<Offer> findByRoute(final String departureCity) {
        return offersDao.findByDepartureCity(departureCity);
    }

    @Override
    public boolean update(int id, Offer updateOffer) {
        return this.offersDao.updateOffer(id, updateOffer);
    }

    @Override
    public boolean remove(int id) {
        return this.offersDao.removeOffer(id);
    }
}
