package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.validator.OfferValidator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class OfferRepository implements OfferInfoRepository {

    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private final OfferValidator offerValidator = new OfferValidator();
    private List<Offer> offerList;



    @PostConstruct
    public void start() {
        offerList = getInfo();
        System.out.println(offerList);
    }

    @PreDestroy
    public void finish() throws IOException {
        FileWriter writer = new FileWriter(FILE_OFFERS, false);
        for (Offer offer : offerList) {
            writer.append(offer.toString());
        }
        writer.flush();
        writer.close();
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
        System.out.println("translational");
        return offerList;
    }


    public Offer saveOffer(Offer offer) {
        offer.setId(autoIncrementId());
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

    @Override
    public Integer getTicketPrice() {
        return offerList.stream().mapToInt(Offer::getPrice).sum();
    }

    private int autoIncrementId() {
        int maxId = 0;
        for (Offer offer : offerList) {
            if (maxId < offer.getId()) {
                maxId = offer.getId();
            }
        }
        return ++maxId;
    }

}
