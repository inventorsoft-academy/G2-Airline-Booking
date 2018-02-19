package com.inventorsoft.dao;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.validator.OfferValidator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class OffersDao implements GetInfo {

    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private final OfferValidator offerValidator = new OfferValidator();
    private List<Offer> offerList = new ArrayList<>();

    @PostConstruct
    public void setInfo() {
        offerList = getInfo();
    }


    public List<Offer> getInfo() {
        List<Offer> offerList = new ArrayList<>();
        File file = new File(FILE_OFFERS);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (offerValidator.validateForAllValues(s)) {
                    Offer offer = new Offer();
                    offer.setId(Integer.parseInt(s[0]));
                    offer.setDepartureCity((s[1]));
                    offer.setArrivalCity(s[2]);
                    offer.setNumberOfSeats(s[5]);
                    offer.setPrice(Integer.parseInt(s[6]));
                    try {
                        offer.setDepartureDate(DATE_FORMAT.parse(s[3]));
                        offer.setArrivalDate(DATE_FORMAT.parse(s[4]));
                    } catch (ParseException e) {
                        System.out.println("Problem in GetInfo method getOffersFromFile()");
                        e.printStackTrace();
                    }
                    offerList.add(offer);
                } else {
                    System.out.println("problem in GetOffersfromFile");
                    System.exit(0);
                }

            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return offerList;
    }


    public List<Offer> getOffers() {
        return offerList;
    }


    public Offer saveOffer(Offer offer) {
        offer.setId(offerList.size());
        offerList.add(offer);
        return offer;
    }

    public Optional<Offer> findByDepartureCity(String departureCity) {
        return offerList.stream().filter(offer -> offer.getDepartureCity().equals(departureCity)).findAny();
    }


    public Optional<Offer> findById(int id) {
        return offerList.stream().filter(offer -> offer.getId() == id).findAny();
    }

    public boolean updateOffer(int id, Offer newOffer) {
        final Optional<Offer> matchOfferOptional = findById(id);
        matchOfferOptional.ifPresent(offer -> offer.update(newOffer));
        return matchOfferOptional.isPresent();
    }

    public boolean removeOffer(int id) {
        return offerList.removeIf(offer -> offer.getId() == id);
    }

}
