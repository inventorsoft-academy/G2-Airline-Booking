package com.inventorsoft.model.offer;

import com.inventorsoft.validator.OfferValidator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OfferOperations {
    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private OfferValidator ov = new OfferValidator();
    private Offer newOffer = new Offer();


    public void importAllOffers(List<Offer> offerList) {
        for (Offer offer : offerList) {
            System.out.println(showOffer(offer));
        }
    }

    private String showOffer(Offer offer) {
        return "Offer{" +
                "id=" + offer.getId() +
                ", route='" + offer.getRoute() + '\'' +
                ", departureDate=" + offer.getDepartureDate() +
                ", arrivalDate=" + offer.getArrivalDate() +
                ", numberOfSeats='" + offer.getNumberOfSeats() + '\'' +
                ", price=" + offer.getPrice() +
                '}';
    }

    public void createOffer(List<Offer> offerList) throws IOException {
        importAllOffers(offerList);

        //auto increment id
        newOffer.setId(ov.autoIncrementId(offerList));
        //set route
        newOffer.setRoute(setNewRoute());

        newOffer.setDepartureDate(setNewDepartureDate());
        newOffer.setArrivalDate(setNewArrivalDate());

        //set number of seats
        newOffer.setNumberOfSeats(setNewNumberOfSeats());
        //set price
        newOffer.setPrice(setNewPrice());

        //watch what we create
        System.out.println("You successful create new offer:" + "\n"
                + showOffer(newOffer));

        //write new information in file
        writeOfferInFile(newOffer);
    }

    private String setNewRoute() {
        Scanner scn = new Scanner(System.in);
        //check route
        System.out.println("Input route:");
        String route = scn.next();

        while (!ov.validateRoute(route)) {
            System.out.println("Please input correct type of route!");
            route = scn.next();
        }

        return route;
    }

    private Date setNewDepartureDate() {
        Scanner scn = new Scanner(System.in);
        //check departure date
        String departureDateValidate;
        Date departureDate = null;
        System.out.println("Input departure date: (dd/mm/yyyy-hh:mm)");
        try {
            while (true) {
                departureDateValidate = scn.next();
                if (ov.validateDate(scn.next())) {
                    if (ov.compareToCurrentDate(DATE_FORMAT.parse(departureDateValidate))) {
                        break;
                    } else {
                        System.out.println("Please don't input date from the past !");
                    }
                } else {
                    System.out.println("Wrong date format! Please input correct type of date (dd/MM/yyyy-kk:mm)");
                }
            }
            departureDate = DATE_FORMAT.parse(departureDateValidate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return departureDate;
    }

    private Date setNewArrivalDate() {
        Scanner scn = new Scanner(System.in);
        //check arrival date
        Date arrivalDate = null;
        String arrivalDateValidate;
        System.out.println("Input arrival date: (dd/mm/yyyy-hh:mm)");
        try {
            //check for compareDepartureDateToArrivalDate and compareToCurrentDate
            while (true) {
                arrivalDateValidate = scn.next();
                if (ov.validateDate(scn.next())) {
                    if (ov.compareToCurrentDate(DATE_FORMAT.parse(arrivalDateValidate))) {
                        if (ov.compareDepartureDateToArrivalDate(DATE_FORMAT.parse(arrivalDateValidate), newOffer.getDepartureDate())) {
                            break;
                        } else {
                            System.out.println("Please don't input earlier arrival date than departure date!");
                        }
                    } else {
                        System.out.println("Please don't input date from the past!");
                    }
                } else {
                    System.out.println("Wrong date format! Please input correct type of date (dd/MM/yyyy-kk:mm)");
                }
            }
            arrivalDate = DATE_FORMAT.parse(arrivalDateValidate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return arrivalDate;
    }

    private String setNewNumberOfSeats() {
        Scanner scn = new Scanner(System.in);
        //check number of seats
        System.out.println("Input number of seats:");
        String numberOfSeats = scn.next();

        while (!ov.validateNumberOfSeats(numberOfSeats)) {
            System.out.println("Please input correct type number of seats(must be bigger than 50)!");
            numberOfSeats = scn.next();
        }

        newOffer.setNumberOfSeats(createNumberOfSeats(numberOfSeats));
        return numberOfSeats;
    }

    private int setNewPrice() {
        Scanner scn = new Scanner(System.in);
        //check price
        System.out.println("Input price for each seats:");
        int price = scn.nextInt();
        if (!ov.validatePrice(price)) {
            System.out.println("Please input correct type of price(must be bigger than 80$)!");
            price = scn.nextInt();
        }

        newOffer.setPrice(price);
        return price;
    }


    /**
     * @param offerList
     * @return changed offerList
     * @throws IOException
     * @throws ParseException
     */
    public List<Offer> editOffer(List<Offer> offerList, int index) throws IOException, ParseException {
        Scanner scn = new Scanner(System.in);
        System.out.println("What offer you want to edit?(id)");
        importAllOffers(offerList);
        int idForEdit = scn.nextInt();
        while (!ov.validateForNull(idForEdit)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForEdit = scn.nextInt();
        }

        Offer changedOffer = findOfferById(offerList, idForEdit);
        offerList.remove(changedOffer);
        if (index == 1) {
            changedOffer.setRoute(setNewRoute());
            offerList.add(changedOffer);
            System.out.println("You successful edit route offer:");
        }
        if (index == 2) {
            Date newDepartureDate = setNewDepartureDate();
            while (newDepartureDate == null) {
                changedOffer.setDepartureDate(setNewDepartureDate());
            }
            changedOffer.setDepartureDate(newDepartureDate);
            offerList.add(changedOffer);
            System.out.println("You successful edit departure date offer:");
        }
        if (index == 3) {
            Date newArrivalDate = setNewArrivalDate();
            while (newArrivalDate == null) {
                changedOffer.setArrivalDate(setNewArrivalDate());
            }
            changedOffer.setArrivalDate(newArrivalDate);
            offerList.add(changedOffer);
            System.out.println("You successful edit arrival date offer:");
        }
        if (index == 4) {
            changedOffer.setNumberOfSeats(setNewNumberOfSeats());
            offerList.add(changedOffer);
            System.out.println("You successful edit number of seats offer:");
        }
        if (index == 5) {
            changedOffer.setPrice(setNewPrice());
            offerList.add(changedOffer);
            System.out.println("You successful edit price offer:");
        }
        System.out.println(showOffer(changedOffer));
        return offerList;
    }


    public void delete(List<Offer> offerList) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("What offer you want to delete?(id)");
        importAllOffers(offerList);
        int idForDelete = scn.nextInt();

        while (!ov.validateForNull(idForDelete)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForDelete = scn.nextInt();
        }
        offerList.remove(findOfferById(offerList, idForDelete));
        System.out.println("You successful delete offer with id:" + idForDelete + "\n");
        importAllOffers(offerList);
        overwriteInFile(offerList);
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

    //only for create method, some comfort with input seats numbers 1,2,3,4,...50 (50)
    private String createNumberOfSeats(String number) {
        String numberOfSeats = "";
        for (int k = 1; k < Integer.parseInt(number); k++) {
            numberOfSeats += String.valueOf(k) + ",";
        }
        numberOfSeats += number;
        return numberOfSeats;
    }

    public void writeOfferInFile(Offer offer) throws IOException {
        FileWriter writer = new FileWriter(FILE_OFFERS, true);
        writer.append(offer.toString());
        writer.flush();
        writer.close();
    }

    public void overwriteInFile(List<Offer> offerList) throws IOException {
        FileWriter writer = new FileWriter(FILE_OFFERS, false);
        for (Offer offer : offerList) {
            writer.append(offer.toString());
        }
        writer.flush();
        writer.close();
    }

    //exit method
    public void exit() {
        System.out.println("Bye-bye, application will be closed");
        System.exit(0);
    }

    //balance of bought tickets
    public void balanceOfBoughtTickets() {

    }

    public boolean validateForEqualsInRange(int value, int number) {
        for (int k = 1; k <= number; k++) {
            if (k == value) {
                return true;
            }
        }
        return false;
    }
}

