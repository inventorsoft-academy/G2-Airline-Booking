/*
package com.inventorsoft.model.offer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OfferOperations {
    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final String FILE_BUFFER_OFFERS = "src/com/TextTest/bufferoffers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private static final Validator validator = new Validator();
    public static Offer offer = new Offer();

    public void createOffer() throws IOException {
        //if we stop project - can be some trash in buffers file
        cleanFile(FILE_BUFFER_OFFERS);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input id offer:");
        offer.setOfferId(validator.validateUniqueId(FILE_OFFERS,validator.validNull(Integer.parseInt(reader.readLine()))));
        System.out.println("Input route:");
        offer.setRoute(validator.validNull(reader.readLine()));
        System.out.println("Input departure time: (dd/mm/yyyy-hh:mm)");
        try {
            offer.setDeparture_time(DATE_FORMAT.parse(reader.readLine()));
        } catch (ParseException e) {
            System.out.println("Wrong date format!");
            System.exit(0);
        }

        System.out.println("Input time of arrival: (dd/mm/yyyy-hh:mm)");
        try {
            offer.setArrival_time(DATE_FORMAT.parse(reader.readLine()));
        } catch (ParseException e) {
            System.out.println("Wrong date format!");
            System.exit(0);
        }

        System.out.println("Input number of seats:");
        offer.setNumber_of_seats(writeNumberOfSeats(validator.validNull(reader.readLine())));

        System.out.println("Input price of seats:");
        offer.setPrice(validator.validNull(Integer.parseInt(reader.readLine())));

        //watch what we create
        System.out.println(offer.toString());
        writeOffer();
    }

    public void importAllOffers() throws IOException {
        //if we stop project - can be some trash in buffers file
        cleanFile(FILE_BUFFER_OFFERS);

        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        fr.close();
    }

    public void editOffer() throws IOException, ParseException {
        //if we stop project - can be some trash in buffers file
        cleanFile(FILE_BUFFER_OFFERS);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input necessary id offer to change:");
        int necessaryId = validator.validNull(Integer.parseInt(reader.readLine()));
        readOffer(necessaryId);
        cleanFile(FILE_OFFERS);
        writeFromBuffer();
        cleanFile(FILE_BUFFER_OFFERS);
    }

    public void delete() throws IOException {
        //if we stop project - can be some trash in buffers file
        cleanFile(FILE_BUFFER_OFFERS);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you want to delete?(id)");
        int idForDelete = validator.validNull(Integer.parseInt(reader.readLine()));
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == idForDelete) {
                continue;
            }
            writeOffer(line);
        }
        br.close();
        fr.close();
        writeFromBuffer();
    }


    //only for create method, some comfort with input seats numbers 1,2,3,4,...50 (50)
    private String writeNumberOfSeats (String number) {
        String numberOfSeats = "";
        for (int k = 1; k < Integer.parseInt(number); k++) {
            numberOfSeats += String.valueOf(k) + ",";
        }
        numberOfSeats += number;
        return numberOfSeats;
    }


    // read offer only for editing, before edit we need to find needs offer
    private void readOffer(int necessaryId) throws IOException, ParseException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == necessaryId) {
                offer.setOfferId(necessaryId);
                offer.setRoute(validator.validNull(s[1]));
                offer.setDeparture_time(DATE_FORMAT.parse(s[2]));
                offer.setArrival_time(DATE_FORMAT.parse(s[3]));
                offer.setNumber_of_seats(validator.validNull(s[4]));
                offer.setPrice(validator.validNull(Integer.parseInt(s[5])));
                System.out.println(offer);
                editFindValue();
                continue;
            }

            writeOffer(line);
        }
        br.close();
        fr.close();
    }

    public void writeOffer() throws IOException {
        FileWriter writer = new FileWriter(FILE_OFFERS, true);
        writer.append(offer.toString());
        writer.flush();
        writer.close();
    }

    public static void writeOffer(Offer offer) throws IOException {
        FileWriter writer = new FileWriter(FILE_BUFFER_OFFERS, true);
        writer.append(offer.toString());
        writer.flush();
        writer.close();
    }

    public void writeOffer(String line) throws IOException {
        FileWriter writer = new FileWriter(FILE_BUFFER_OFFERS, true);
        line += "\n";
        writer.append(line);
        writer.flush();
        writer.close();
    }

    public void writeFromBuffer() throws IOException {
        File file = new File(FILE_BUFFER_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        FileWriter writer = new FileWriter(FILE_OFFERS, false);
        String line;
        while ((line = br.readLine()) != null) {
            line += "\n";
            writer.append(line);
        }
        br.close();
        fr.close();
        writer.flush();
        writer.close();
    }

    public void cleanFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write("");
        fileWriter.close();
    }

    private void editFindValue() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What do you want to change?(route, departure time, arrival time, number of seats, price)");
        String necessaryField;
        while (true) {
            necessaryField = validator.validNull(reader.readLine());
            if (necessaryField.equals("route")
                    || necessaryField.equals("departure time")
                    || necessaryField.equals("arrival time")
                    || necessaryField.equals("number of seats")
                    || necessaryField.equals("price")) { break; }
        }

        switch (necessaryField) {
            case "route":
                System.out.println("Input new route");
                offer.setRoute(validator.validNull(reader.readLine()));
                break;
            case "departure time":
                System.out.println("Input new departure time:");
                offer.setDeparture_time(DATE_FORMAT.parse(reader.readLine()));
                break;
            case "arrival time":
                System.out.println("Input new arrival time:");
                offer.setArrival_time(DATE_FORMAT.parse(reader.readLine()));
                break;
            case "number of seats":
                System.out.println("Input new number of seats:");
                offer.setNumber_of_seats(writeNumberOfSeats(validator.validNull(reader.readLine())));
                break;
            case "price":
                System.out.println("Input new price:");
                offer.setPrice(validator.validNull(Integer.parseInt(reader.readLine())));
                break;
        }
        writeOffer(offer);
    }




}
*/
