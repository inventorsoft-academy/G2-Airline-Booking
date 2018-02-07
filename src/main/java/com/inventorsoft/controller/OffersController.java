package com.inventorsoft.controller;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.view.OfferView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.inventorsoft.console.Console.logger;

public class OffersController {
    private List<Offer> offerList = new ArrayList<>();


    public void createOffer(List<Offer> offerList, String route,
                            Date departureDate,
                            Date arrivalDate,
                            String numberOfSeats,
                            int price) {
        Offer offer = new Offer();

        offer.setId(autoIncrementId(offerList));
        offer.setRoute(route);
        offer.setDepartureDate(departureDate);
        offer.setArrivalDate(arrivalDate);
        offer.setNumberOfSeats(createNumberOfSeats(numberOfSeats));
        offer.setPrice(price);

        offerList.add(offer);

        this.offerList = offerList;
    }

    public int autoIncrementId(final List<Offer> offerList) {
        int maxId = 0;
        for (Offer offer : offerList) {
            if (maxId < offer.getId()) {
                maxId = offer.getId();
            }

        }
        return ++maxId;
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

    public void editOffer(List<Offer> offerList, int index, int idForEdit, OfferView view) {
        Offer changedOffer = findOfferById(offerList, idForEdit);
        offerList.remove(changedOffer);
        switch (index) {
            case 1:
                changedOffer.setRoute(view.setNewRoute());
                break;
            case 2:
                Date newDepartureDate = view.setNewDepartureDate();
                while (newDepartureDate == null) {
                    changedOffer.setDepartureDate(view.setNewDepartureDate());
                }
                changedOffer.setDepartureDate(newDepartureDate);
                break;
            case 3:
                Date newArrivalDate = view.setNewArrivalDate();
                while (newArrivalDate == null) {
                    changedOffer.setArrivalDate(view.setNewArrivalDate());
                }
                changedOffer.setArrivalDate(newArrivalDate);
                break;
            case 4:
                changedOffer.setNumberOfSeats(view.setNewNumberOfSeats());
                break;
            case 5:
                changedOffer.setPrice(view.setNewPrice());
                break;
        }

        offerList.add(changedOffer);

        this.offerList = offerList;
    }

    //find offer by needs id
    private Offer findOfferById(List<Offer> offerList, int index) {
        for (Offer offer : offerList) {
            if (offer.getId() == index) {
                return offer;
            }
        }
        return null;
    }

    public void delete(List<Offer> offerList, int idForDelete) throws IOException {
        offerList.remove(findOfferById(offerList, idForDelete));

        this.offerList = offerList;
    }


    public List<Offer> searchOffer(List<Offer> offerList, String location, Date departureDate) {
        List<Offer> searchOffers = new ArrayList<>();
        searchOffers = searchOfferByLocation(offerList, searchOffers, location);
        searchOffers = searchOfferByDepartureDate(offerList, searchOffers, departureDate);
        return searchOffers;
    }

    private List<Offer> searchOfferByLocation(List<Offer> offerList, List<Offer> searchOffers, String location) {
        logger.info("searchOfferByLocation find these offers:");
        for (Offer offer : offerList) {
            String array[] = offer.getRoute().split("->");
            if (array[0].equals(location)) {
                searchOffers.add(offer);
                logger.info(offer.toString());
            }
        }
        return searchOffers;

    }

    private List<Offer> searchOfferByDepartureDate(List<Offer> offerList, List<Offer> searchOffers, Date departureDate) {
        logger.info("searchOfferByDepartureDate filter these offers:");
        logger.info(searchOffers.toString());
        Calendar plusThreeDays = Calendar.getInstance();
        plusThreeDays.setTime(departureDate);
        plusThreeDays.add(Calendar.DAY_OF_MONTH, 3);
        Calendar minusThreeDays = Calendar.getInstance();
        minusThreeDays.setTime(departureDate);
        plusThreeDays.add(Calendar.DAY_OF_MONTH, -3);

        for (Offer offer : offerList) {
            Calendar offerDate = Calendar.getInstance();
            offerDate.setTime(offer.getDepartureDate());
            if (offerDate.compareTo(plusThreeDays) > 0 && offerDate.compareTo(minusThreeDays) < 0) {
                searchOffers.remove(offer);
            }
        }
        logger.info("after searchOfferByDepartureDate method left offers:");
        logger.info(searchOffers.toString());
        return searchOffers;
    }

}
