package com.inventorsoft.service;


import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.repository.TestOfferRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestOfferService extends GeneralService<Offer, Integer> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    TestOfferRepository testOfferRepository;

    public TestOfferService(TestOfferRepository repository) {
        super(repository);
        this.testOfferRepository = repository;
    }

    @Transactional(readOnly = true)
    public List<Offer> getAll() {
        return testOfferRepository.findAll();
    }

    @Transactional
    public Offer createTestOffer() {
        Offer offer = new Offer();
        try {
            offer.setDepartureDate(DATE_FORMAT.parse("12/02/2018-12:04"));
            offer.setArrivalDate(DATE_FORMAT.parse("12/02/2018-17:04"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offer.setDepartureCity("ASD");
        offer.setArrivalCity("QWE");
        offer.setNumberOfSeats("1,2,3,4,5");
        offer.setPrice(80);
        return testOfferRepository.save(offer);
    }

}