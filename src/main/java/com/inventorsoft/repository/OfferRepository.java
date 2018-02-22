package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.validator.OfferValidator;
import com.inventorsoft.xml.ReadXMLFileDOMExample;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class OfferRepository implements OfferInfoRepository {

    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private List<Offer> offerList = new ArrayList<>();
    private List<Offer> customerOfferList = new ArrayList<>();

    private ReadXMLFileDOMExample xml = new ReadXMLFileDOMExample();
    private Map<String, String> cities = xml.getCities();

    @PostConstruct
    public void start() {
        offerList = getInfo();
        customerOfferList = getInfo();
        customerOfferList.forEach(offer -> {
            offer.setDepartureCity(replaceCityCodeToCityName(offer.getDepartureCity(), cities));
            offer.setArrivalCity(replaceCityCodeToCityName(offer.getArrivalCity(), cities));
        });
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


    private List<Offer> getInfo() {
        List<Offer> offerList = new ArrayList<>();
        File file = new File(FILE_OFFERS);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (new OfferValidator().validateForAllValues(s)) {
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

    @Override
    public List<Offer> getOffersForCustomer() {
        return customerOfferList;
    }

    @Override
    public List<Offer> searchOffers(String departureCity, String departureDate) {
        List<Offer> searchOffersList = new ArrayList<>();
        System.out.println(searchOffersList);
        searchOffersList = deleteNotCorrectOffersFromCustomerRequest(searchOffersList, departureCity, departureDate);
        System.out.println(searchOffersList);
        System.out.println(offerList);
        return searchOffersList;
    }

    private List<Offer> deleteNotCorrectOffersFromCustomerRequest(List<Offer> searchOffersList,
                                                                 String departureCity,
                                                                 String departureDate) {
        System.out.println("method add some offers");

        customerOfferList.forEach(offer -> {
            System.out.println(offer.getDepartureCity());
            System.out.println(departureCity);
            System.out.println(offer.getDepartureDate());

            try {
                System.out.println(DATE_FORMAT.parse(departureDate));
                if (offer.getDepartureCity().equals(departureCity) &&
                      offer.getDepartureDate().equals(DATE_FORMAT.parse(departureDate))) {
                    searchOffersList.add(offer);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return searchOffersList;
    }
    private String replaceCityCodeToCityName(String code, Map<String, String> cities) {
        System.out.println("replaceCityCodeToCityName");
        for (Map.Entry<String, String> entry : cities.entrySet()) {
            if (entry.getKey().equals(code)) {
                return entry.getValue();
            }
        }
        return "fail";
    }

    public Offer saveOffer(Offer offer) {
        offer.setId(autoIncrementId());
        offer.setNumberOfSeats(offer.getNumberOfSeats());
        offerList.add(offer);
        System.out.println(offer);
        return offer;
    }

    //only for create method, some comfort with input seats numbers 1,2,3,4,...50 (50)
    private String createNumberOfSeats(String number) {
        String numberOfSeats = "";
        for (int k = 1; k < Integer.parseInt(number); k++) {
            numberOfSeats += String.valueOf(k) + ",";
        }
        numberOfSeats += number;
        return numberOfSeats;
    }

    public Optional<Offer> findByDepartureCity(String departureCity) {
        return offerList.stream().filter(offer -> offer.getDepartureCity().equals(departureCity)).findAny();
    }


    public Optional<Offer> findById(int id) {
        return offerList.stream().filter(offer -> offer.getId() == id).findAny();
    }

    public boolean updateOffer(int id, Offer newOffer) {
        final Optional<Offer> matchOfferOptional = findById(id);

        matchOfferOptional.ifPresent(offer ->
        {
            offerList.remove(offer);
            offer.update(newOffer);
            offer.setNumberOfSeats(offer.getNumberOfSeats());
            offerList.add(offer);
        });
        System.out.println(offerList);
        return matchOfferOptional.isPresent();
    }

    public boolean removeOffer(int id) {
        return offerList.removeIf(offer -> offer.getId() == id);
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
