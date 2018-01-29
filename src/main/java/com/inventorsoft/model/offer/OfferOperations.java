package com.inventorsoft.model.offer;

import com.inventorsoft.validator.OfferValidator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OfferOperations {
    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private OfferValidator ov = new OfferValidator();
    private Offer newOffer;



    private void importAllOffers(List<Offer> offerList) {
        for (Offer offer: offerList) {
            System.out.println(showOffer(offer));
        }
    }

    private String showOffer(Offer offer) {
        return ("Offer{" +
                    "id=" + offer.getId() +
                    ", route='" + offer.getRoute() + '\'' +
                    ", departureDate=" + offer.getDepartureDate() +
                    ", arrivalDate=" + offer.getArrivalDate() +
                    ", numberOfSeats='" + offer.getNumberOfSeats() + '\'' +
                    ", price=" + offer.getPrice() +
                    '}');
    }

    public void createOffer(List<Offer> offerList) throws IOException {
        if (askAdminIfHeWantToSeeAllOffers()) {
            importAllOffers(offerList);
        }
        //auto increment id
        newOffer.setId(ov.autoIncrementId(offerList));
        //check route
        newOffer.setRoute(setNewRoute());
        //check departure time
        newOffer.setDepartureDate(setNewDepartureDate());
        //check arrival time
        newOffer.setArrivalDate(setNewArrivalDate());
        //check number of seats
        newOffer.setNumberOfSeats(setNewNumberOfSeats());
        //check price
        newOffer.setPrice(setNewPrice());

        //watch what we create
        System.out.println("You successful create new offer:" + "\n"
        + showOffer(newOffer));

        //write new information in file
        writeOfferInFile(newOffer);
    }

    private boolean askAdminIfHeWantToSeeAllOffers() {
        String answer = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Do you want to see all offers?" + "\n"
                + "1 - yes" + "\n"
                + "2 - no");
        try {
            answer = br.readLine();
            while (!ov.validateForOneOrTwo(answer)) {
                System.out.println("Please input 1 or 2!");
                answer = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Problem in OffersOperation method askAdminIfHeWantToSeeAllOffers()" + answer);
        }

        return !answer.equals("2");
    }

    private String setNewRoute() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check route
        String route = "";
        System.out.println("Input route:");
        try {
            route = br.readLine();
            while (!ov.validateRoute(route)) {
                System.out.println("Please input correct type of route!");
                route = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return route;
    }

    private boolean setNewDepartureDate() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check departure date
        Date departureDate = null;
        System.out.println("Input departure date: (dd/mm/yyyy-hh:mm)");
        try {
            while (!ov.compareToCurrentDate(DATE_FORMAT.parse(br.readLine()))) {
                System.out.println("Please don't input date from the past !");
                departureDate = DATE_FORMAT.parse(br.readLine());
            }
        } catch (ParseException e) {
            System.out.println("Wrong date format! Please input correct type of date (dd/MM/yyyy-kk:mm)");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        newOffer.setDepartureDate(departureDate);
        return true;
    }

    private boolean setNewArrivalDate() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check arrival date
        Date arrivalDate = null;
        System.out.println("Input arrival date: (dd/mm/yyyy-hh:mm)");
        try {
            //check for compareDepartureDateToArrivalDate and compareToCurrentDate
            while (true) {
                arrivalDate = DATE_FORMAT.parse(br.readLine());
                if (ov.compareToCurrentDate(arrivalDate)) {
                    if (ov.compareDepartureDateToArrivalDate(arrivalDate, newOffer.getDepartureDate())) {
                        break;
                    }
                    else {
                        System.out.println("Please don't input earlier arrival date than departure date!");
                    }
                }
                else {
                    System.out.println("Please don't input date from the past!");
                }
            }
        } catch (ParseException e) {
            System.out.println("Wrong date format! Please input correct type of date (dd/MM/yyyy-kk:mm)");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        newOffer.setArrivalDate(arrivalDate);
        return true;
    }

    private boolean setNewNumberOfSeats() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check number of seats
        String numberOfSeats = "";
        System.out.println("Input number of seats:");
        try {
            numberOfSeats = br.readLine();
            if (!ov.validateNumberOfSeats(numberOfSeats)) {
                System.out.println("Please input correct type number of seats(must be bigger than 50)!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        newOffer.setNumberOfSeats(createNumberOfSeats(numberOfSeats));
        return true;
    }

    private boolean setNewPrice() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //check price
        int price = 0;
        System.out.println("Input price for each seats:");
        try {
            price = br.read();
            if (!ov.validatePrice(price)) {
                System.out.println("Please input correct type of price(must be bigger than 80$)!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        newOffer.setPrice(price);
        return true;
    }


    /**
     *
     * @param offerList
     * @return changed offerList
     * @throws IOException
     * @throws ParseException
     */
    public void editOffer(List<Offer> offerList, int value, int index) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you want to edit?(id)");
        importAllOffers(offerList);
        int idForEdit = br.read();
        while (!ov.validateForNull(idForEdit)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForEdit = br.read();
        }

        Offer changedOffer = findOfferById(offerList, idForEdit);
        offerList.remove(findOfferById(offerList, idForEdit));
        if (index == 5) {
            changedOffer.setPrice(value);
            offerList.add(changedOffer);
            System.out.println("You successful edit price offer:");
        }
        System.out.println(showOffer(changedOffer));
    }

    public void editOffer(List<Offer> offerList, String value, int index) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you want to edit?(id)");
        importAllOffers(offerList);
        int idForEdit = br.read();
        while (!ov.validateForNull(idForEdit)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForEdit = br.read();
        }

        Offer changedOffer = findOfferById(offerList, idForEdit);
        offerList.remove(findOfferById(offerList, idForEdit));
        if (index == 1) {
            changedOffer.setRoute(value);
            offerList.add(changedOffer);
            System.out.println("You successful edit route offer:");
        }
        if (index == 4) {
            changedOffer.setNumberOfSeats(value);
            offerList.add(changedOffer);
            System.out.println("You successful edit number of seats offer:");
        }
        System.out.println(showOffer(changedOffer));
    }

    public void editOffer(List<Offer> offerList, Date value, int index) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you want to edit?(id)");
        importAllOffers(offerList);
        int idForEdit = br.read();
        while (!ov.validateForNull(idForEdit)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForEdit = br.read();
        }

        Offer changedOffer = findOfferById(offerList, idForEdit);
        offerList.remove(findOfferById(offerList, idForEdit));
        if (index == 2) {
            changedOffer.setDepartureDate(value);
            offerList.add(changedOffer);
            System.out.println("You successful edit id offer:");
        }
        if (index == 3) {
            changedOffer.setArrivalDate(value);
            offerList.add(changedOffer);
            System.out.println("You successful edit price offer:");
        }
        System.out.println(showOffer(changedOffer));
    }

    public void delete(List<Offer> offerList) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you want to delete?(id)");
        importAllOffers(offerList);
        int idForDelete = br.read();
        while (!ov.validateForNull(idForDelete)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForDelete = br.read();
        }
        offerList.remove(findOfferById(offerList, idForDelete));
        System.out.println("You successful delete offer with id:" + idForDelete + "\n");
        importAllOffers(offerList);
        overwriteInFile(offerList);
    }


    //find offer by needs id
    private Offer findOfferById(List<Offer> offerList, int index) {
        for (Offer offer: offerList) {
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
        for (Offer offer: offerList) {
            writer.append(offer.toString());
        }
        writer.flush();
        writer.close();
    }

    /*public static void writeOfferInFile(Offer offer) throws IOException {
        FileWriter writer = new FileWriter(FILE_BUFFER_OFFERS, true);
        writer.append(offer.toString());
        writer.flush();
        writer.close();
    }

    public void writeOfferInFile(String line) throws IOException {
        FileWriter writer = new FileWriter(FILE_BUFFER_OFFERS, true);
        line += "\n";
        writer.append(line);
        writer.flush();
        writer.close();
    }*/



    private void editFindValue() throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What do you want to change?" + "\n"
                + "route - 1" + "\n"
                + "departure time - 2" + "\n"
                + "arrival time - 3" + "\n"
                + "number of seats - 4" + "\n"
                + "price - 5");
        int necessaryField = br.read();
        while (!ov.validateForEqualsInRange(necessaryField,5)) {
            System.out.println("Please input number from 1 to 5!");
            necessaryField = br.read();
        }

        switch (necessaryField) {
            case 1:
                System.out.println("Input new route");
                offer.setRoute(validator.validNull(reader.readLine()));
                break;
            case 2:
                System.out.println("Input new departure time:");
                offer.setDeparture_time(DATE_FORMAT.parse(reader.readLine()));
                break;
            case 3:
                System.out.println("Input new arrival time:");
                offer.setArrival_time(DATE_FORMAT.parse(reader.readLine()));
                break;
            case 4:
                System.out.println("Input new number ov seats:");
                offer.setNumber_of_seats(createNumberOfSeats(validator.validNull(reader.readLine())));
                break;
            case 5:
                System.out.println("Input new price:");
                offer.setPrice(validator.validNull(Integer.parseInt(reader.readLine())));
                break;
        }
        writeOffer(offer);
    }




}

