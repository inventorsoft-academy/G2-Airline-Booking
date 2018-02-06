package com.inventorsoft.view;

import com.inventorsoft.controller.OffersController;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.validator.OfferValidator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OfferView {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private OfferValidator validator = new OfferValidator();
    private OffersController offersController = new OffersController();
    // need to setNewArrivalDate
    private Date departureDate = null;


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

        offersController.createOffer(offerList,
                setNewRoute(),
                setNewDepartureDate(),
                setNewArrivalDate(),
                setNewNumberOfSeats(),
                setNewPrice());

        System.out.println("You successful create new offer!");
    }



    public String setNewRoute() {
        Scanner scn = new Scanner(System.in);
        //check route
        System.out.println("Input route:");
        String route = scn.next();

        while (!validator.validateRoute(route)) {
            System.out.println("Please input correct type of route!");
            route = scn.next();
        }
        return route;
    }

    public Date setNewDepartureDate() {
        Scanner scn = new Scanner(System.in);
        //check departure date
        String departureDateValidate;
        System.out.println("Input departure date: (dd/mm/yyyy-hh:mm)");
        try {
            while (true) {
                departureDateValidate = scn.next();
                if (validator.compareToCurrentDate(departureDateValidate)) {
                    break;
                } else {
                    System.out.println("Please don't input date from the past !");
                }
            }
            departureDate = DATE_FORMAT.parse(departureDateValidate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return departureDate;
    }

    public Date setNewArrivalDate() {
        Scanner scn = new Scanner(System.in);
        //check arrival date
        Date arrivalDate = null;
        String arrivalDateValidate;
        System.out.println("Input arrival date: (dd/mm/yyyy-hh:mm)");
        try {
            //check for compareDepartureDateToArrivalDate and compareToCurrentDate
            while (true) {
                arrivalDateValidate = scn.next();
                if (validator.compareToCurrentDate(arrivalDateValidate)) {
                    if (validator.compareDepartureDateToArrivalDate(DATE_FORMAT.parse(arrivalDateValidate),
                            departureDate)) {
                        break;
                    } else {
                        System.out.println("Please don't input earlier arrival date than departure date!");
                    }
                } else {
                    System.out.println("Please don't input date from the past!");
                }
            }
            arrivalDate = DATE_FORMAT.parse(arrivalDateValidate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return arrivalDate;
    }

    public String setNewNumberOfSeats() {
        Scanner scn = new Scanner(System.in);
        //check number of seats
        System.out.println("Input number of seats:");
        int numberOfSeats = scn.nextInt();

        while (!validator.validateNumberOfSeats(numberOfSeats)) {
            System.out.println("Please input correct type number of seats(must be bigger than 50)!");
            numberOfSeats = scn.nextInt();
        }
        return String.valueOf(numberOfSeats);
    }

    public int setNewPrice() {
        Scanner scn = new Scanner(System.in);
        //check price
        System.out.println("Input price for each seats:");
        int price = scn.nextInt();
        if (!validator.validatePrice(price)) {
            System.out.println("Please input correct type of price(must be bigger than 80$)!");
            price = scn.nextInt();
        }

        return price;
    }

    public void editOffer(List<Offer> offerList, int index) {
        importAllOffers(offerList);

        Scanner scn = new Scanner(System.in);
        System.out.println("What offer you want to edit?(id)");
        int idForEdit = scn.nextInt();
        while (!validator.validateForExistence(idForEdit, offerList)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForEdit = scn.nextInt();
        }

        offersController.editOffer(offerList,index,idForEdit, new OfferView());

        System.out.println("You successful edit offer!");
    }


    public void delete(List<Offer> offerList) throws IOException {
        importAllOffers(offerList);

        Scanner scn = new Scanner(System.in);
        System.out.println("What offer you want to delete?(id)");
        int idForDelete = scn.nextInt();

        while (!validator.validateForExistence(idForDelete, offerList)) {
            System.out.println("Please input correct type of id!(must be >= 0)");
            idForDelete = scn.nextInt();
        }

        offersController.delete(offerList,idForDelete);

        System.out.println("You successful delete offer!");
    }

    public String askLocation() {
        Scanner scn = new Scanner(System.in);
        //check location
        System.out.println("Input your location:");
        String location = scn.next();

        while (!validator.validateLocation(location)) {
            System.out.println("Please input correct type of location!");
            location = scn.next();
        }
        return location;
    }


    //search ticket by any value
    public void searchOffer(List<Offer> offerList) {
        System.out.println("Please input your location and departure date:");

        System.out.println(offersController.searchOffer(offerList,askLocation(), setNewDepartureDate()));

    }

}
