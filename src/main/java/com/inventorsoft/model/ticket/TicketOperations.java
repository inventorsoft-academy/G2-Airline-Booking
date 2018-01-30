
package com.inventorsoft.model.ticket;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.validator.OfferValidator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TicketOperations {
    private static final String FILE_TICKETS = "src/main/resources/tickets.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private OfferValidator ov = new OfferValidator();
    private Ticket newTicket = new Ticket();


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


    private String showTicket(Ticket ticket) {
        return "Ticket{" +
                "name='" + ticket.getName() + '\'' +
                ", customerId=" + ticket.getCustomerId() +
                ", offerId=" + ticket.getOfferId() +
                ", route='" + ticket.getRoute() + '\'' +
                ", departure_time=" + ticket.getDepartureDate() +
                ", arrival_time=" + ticket.getArrivalDate() +
                ", number='" + ticket.getNumber() + '\'' +
                ", price=" + ticket.getPrice() +
                '}';
    }

    public void bookTicket(List<Offer> offerList) throws IOException {
        importAllOffers(offerList);

        //auto increment id
        newOffer.setId(ov.autoIncrementId(offerList));
        //set route
        newOffer.setRoute(setNewRoute());

        //check for ParseException(null) and set departure time
        Date newDepartureDate = setNewDepartureDate();
        while (newDepartureDate == null) {
            newOffer.setDepartureDate(setNewDepartureDate());
        }
        newOffer.setDepartureDate(newDepartureDate);

        //check for ParseException(null) and set arrival time
        Date newArrivalDate = setNewArrivalDate();
        while (newArrivalDate == null) {
            newOffer.setArrivalDate(setNewArrivalDate());
        }
        newOffer.setArrivalDate(newArrivalDate);

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

    private boolean askAdminIfHeWantToSeeAllOffers() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Do you want to see all offers?" + "\n"
                + "1 - yes" + "\n"
                + "2 - no");

        int answer = scn.nextInt();
        while (!validateForEqualsInRange(answer, 2)) {
            System.out.println("Please input 1 or 2!");
            answer = scn.nextInt();
        }
        return !(answer == 2);
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
        Date departureDate = null;
        System.out.println("Input departure date: (dd/mm/yyyy-hh:mm)");
        try {
            while (!ov.compareToCurrentDate(DATE_FORMAT.parse(scn.next()))) {
                System.out.println("Please don't input date from the past !");
                departureDate = DATE_FORMAT.parse(scn.next());
            }
        } catch (ParseException e) {
            System.out.println("Wrong date format! Please input correct type of date (dd/MM/yyyy-kk:mm)");
            return null;
        }
        return departureDate;
    }

    private Date setNewArrivalDate() {
        Scanner scn = new Scanner(System.in);
        //check arrival date
        Date arrivalDate = null;
        System.out.println("Input arrival date: (dd/mm/yyyy-hh:mm)");
        try {
            //check for compareDepartureDateToArrivalDate and compareToCurrentDate
            while (true) {
                arrivalDate = DATE_FORMAT.parse(scn.next());
                if (ov.compareToCurrentDate(arrivalDate)) {
                    if (ov.compareDepartureDateToArrivalDate(arrivalDate, newOffer.getDepartureDate())) {
                        break;
                    } else {
                        System.out.println("Please don't input earlier arrival date than departure date!");
                    }
                } else {
                    System.out.println("Please don't input date from the past!");
                }
            }
        } catch (ParseException e) {
            System.out.println("Wrong date format! Please input correct type of date (dd/MM/yyyy-kk:mm)");
            return null;
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


    public static Ticket ticket = new Ticket();

    //for customer
    void viewTicketBalance() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("In which offer you want to see the tickets balance?(offerId)");
        necessaryOfferId = validator.validNull(reader.readLine());
        ticketBalance(necessaryOfferId);
    }

    //search ticket by any value
    void searchTicket() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You want to search by?(id, route, departure time, arrival time)");
        necessaryOfferId = validator.validNull(reader.readLine());
        switch (necessaryOfferId) {
            case "id":
                System.out.println("Enter offer id:");
                searchTicketByValue(validator.validNull(String.valueOf(reader.readLine())));
                break;
            case "route":
                System.out.println("Enter offer route:");
                searchTicketByValue(validator.validNull(reader.readLine()));
                break;
            case "departure time":
                System.out.println("Enter offer departure time:");
                searchTicketByValue(validator.validNull(reader.readLine()));
                break;
            case "arrival time":
                System.out.println("Enter offer arrival time:");
                searchTicketByValue(validator.validNull(reader.readLine()));
                break;
        }
    }

    //book ticket
    void bookTicket() throws IOException, ParseException {
        buyTicket();
        FileWriter writer = new FileWriter(FILE_TICKETS, true);
        writer.append(ticket.toString());
        writer.flush();
        writer.close();
    }

    //review ticket
    void reviewTickets() throws IOException {
        File file = new File(FILE_TICKETS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (s[0].equals(com.IO.IdentifyPath.customer.getName())) {
                System.out.println(line);
            }
        }
        br.close();
        fr.close();
    }

    private void ticketBalance(String necessaryOfferId) throws IOException, ParseException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (s[0].equals(necessaryOfferId)
                    || s[1].equals(necessaryOfferId)
                    || s[2].equals(necessaryOfferId)
                    || s[3].equals(necessaryOfferId)
                    || s[4].equals(necessaryOfferId)
                    || s[5].equals(necessaryOfferId)) {
                offer.setOfferId(Integer.parseInt(s[0]));
                offer.setRoute(validator.validNull(s[1]));
                offer.setDeparture_time(DATE_FORMAT.parse(s[2]));
                offer.setArrival_time(DATE_FORMAT.parse(s[3]));
                offer.setNumber_of_seats(validator.validNull(s[4]));
                offer.setPrice(Integer.parseInt(s[5]));
            }
        }
        br.close();
        fr.close();
        System.out.println(offer.getNumber_of_seats());

    }

    private void searchTicketByValue(String necessaryValue) throws IOException, ParseException {
        System.out.println("Available number of seats:");
        ticketBalance(necessaryValue);
    }

    private void buyTicket() throws IOException, ParseException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        fr.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you are interested in?(offerId)");
        String necessaryId = reader.readLine();
        ticketBalance(validator.validNull(necessaryId));

        System.out.println("What ticket you want to buy?(number)");
        String number = validator.validNull(reader.readLine());

        String numberOfSeats = "";
        String[] s = offer.getNumber_of_seats().split(",");
        for (int k = 0; k < s.length; k++) {
            boolean checker = true;
            //check if only one ticket
            if (s.length == 1) {
                checker = false;
            }
            //check needs ticket
            if (s[k].equals(number)) {
                checker = false;
            }
            //check and write last ticket
            if (k+2 == s.length && s[k+1].equals(number)) {
                numberOfSeats += s[k];
                break;
            }
            //correct write last ticket
            if (k+1 == s.length) {
                checker = false;
                numberOfSeats += s[k];
            }

            //write ticket
            if (checker) {
                numberOfSeats += s[k] + ",";
            }
        }
        System.out.println(numberOfSeats);
        offer.setNumber_of_seats(numberOfSeats);

        file = new File(FILE_OFFERS);
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        while ((line = br.readLine()) != null) {
            s = line.split(" ");
            if (s[0].equals(necessaryId)) {
                OfferOperations.writeOffer(offer);
                continue;
            }
            offerOperations.writeOffer(line);
        }

        ticket.setName(com.IO.IdentifyPath.customer.getName());
        ticket.setCustomerId(com.IO.IdentifyPath.customer.getId());
        ticket.setOfferId(offer.getOfferId());
        ticket.setRoute(offer.getRoute());
        ticket.setDepartureDate(offer.getDeparture_time());
        ticket.setArrivalDate(offer.getArrival_time());
        ticket.setNumber(number);
        ticket.setPrice(offer.getPrice());
        br.close();
        fr.close();
        offerOperations.writeFromBuffer();
        offerOperations.cleanFile(FILE_BUFFER_OFFERS);
    }

    void downloadTicketToFile() {
        //NOP

    }





}
*/
