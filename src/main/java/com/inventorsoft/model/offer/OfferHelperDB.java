

// this is not work! need refactor

//Date timme or datetime!!!???

package com.inventorsoft.model.offer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class OfferHelperDB {

    private static Offer offer = new Offer();
    private static final String URl = "jdbc:mysql://localhost:3306/airline-booking?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String INSERT_IN_ADMINS = "Insert into admins (login, password,email) values (?,?,?)";
    private static final String INSERT_IN_CUSTOMERS = "Insert into customers(login,password,email) values (?,?,?)";
    private static final String GET_ALL_OFFERS = "select * from offers";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private String answer;

    public void start() throws IOException, ParseException {
        identifyOperation();
        implementPath();
    }

    private void implementPath() throws IOException, ParseException {
        switch (answer) {
            case "c":
                //createOffer();
                break;
            case "e":
                //editOffer();
                break;
            case "d":
                //delete();
                break;
            case "i":
                importAllOffers();
                break;
        }
    }

    private void importAllOffers() throws IOException {
        try {
            Connection connection = DriverManager.getConnection(URl, USERNAME, PASSWORD);
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(GET_ALL_OFFERS)) {
                    if (rs.next()) {
                        System.out.print(rs.getInt("offerId") + " ");
                        System.out.print(rs.getString("route") + " ");
                        System.out.print(rs.getDate("departure_time") + " ");
                        System.out.print(rs.getDate("arrival_time") + " ");
                        System.out.println(rs.getString("number_of_seats"));
                    }
                }
            }

        }
        catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void identifyOperation() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Create edit delete or import all offers?(c/e/d/i)");
        while (true) {
            answer = validNull(reader.readLine());
            if (answer.equals("c") || answer.equals("e") || answer.equals("d") || answer.equals("i")) break;
        }
    }

    private String validNull(String value){
        while (value.equals("")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                value = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
    /*
    private int validateUniqueId(int id) throws IOException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            while (Integer.parseInt(s[0]) == id) {
                id++;
            }
        }
        br.close();
        fr.close();
        return id;
    }



    private int validNull(int value){
        while (value <= 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                value = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    private void createOffer() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input id offer:");
        offer.setOfferId(validateUniqueId(validNull(Integer.parseInt(reader.readLine()))));
        System.out.println("Input route:");
        offer.setRoute(validNull(reader.readLine()));
        System.out.println("Input departure time: (dd/mm/yyyy-hh:mm)");
        try {
            offer.setDeparture_time(DATE_FORMAT.parse(reader.readLine()));
        } catch (ParseException e) {
            System.out.println("Wrong date format!");
            System.exit(0);
        }

        System.out.println("Input Time Of Arrival: (dd/mm/yyyy-hh:mm)");
        try {
            offer.setArrival_time(DATE_FORMAT.parse(reader.readLine()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println("Input Number Of Seats:");
        offer.setNumberOfSeats(writeNumberOfSeats(validNull(reader.readLine())));
        System.out.println(offer.toString());
        writeOfferInFile();
    }

    private void editOffer() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input necessary id offer to change:");
        int necessaryId = validNull(Integer.parseInt(reader.readLine()));
        readOffer(necessaryId);
        cleanFile(FILE_OFFERS);
        writeFromBuffer();
        cleanFile(FILE_BUFFER_OFFERS);
    }

    private void readOffer(int necessaryId) throws IOException, ParseException {
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == necessaryId) {
                offer.setOfferId(necessaryId);
                offer.setRoute(validNull(s[1]));
                offer.setDeparture_time(DATE_FORMAT.parse(s[2]));
                offer.setArrival_time(DATE_FORMAT.parse(s[3]));
                offer.setNumberOfSeats(validNull(s[4]));
                System.out.println(offer);
                editFindValue();
                continue;
            }

            writeOfferInFile(line);
        }
        br.close();
        fr.close();
    }


    private void delete() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What offer you want to delete?(id)");
        int idForDelete = validNull(Integer.parseInt(reader.readLine()));
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == idForDelete) {
                continue;
            }
            writeOfferInFile(line);
        }
        br.close();
        fr.close();
        writeFromBuffer();
    }



    private void writeOfferInFile() throws IOException {
        FileWriter writer = new FileWriter(FILE_OFFERS, true);
        writer.append(offer.toString());
        writer.flush();
        writer.close();
    }

    private void writeOfferInFile(Offer offer) throws IOException {
        FileWriter writer = new FileWriter(FILE_BUFFER_OFFERS, true);
        writer.append(offer.toString());
        writer.flush();
        writer.close();
    }

    private void writeOfferInFile(String line) throws IOException {
        FileWriter writer = new FileWriter(FILE_BUFFER_OFFERS, true);
        line += "\n";
        writer.append(line);
        writer.flush();
        writer.close();
    }

    private void writeFromBuffer() throws IOException {
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

    private void editFindValue() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What do you want to change?(route, departure_time, arrival_time, number_of_seats)");
        String necessaryField;
        while (true) {
            necessaryField = validNull(reader.readLine());
            if (necessaryField.equals("route")
                    || necessaryField.equals("departure_time")
                    || necessaryField.equals("arrival_time")
                    || necessaryField.equals("number_of_seats")) { break; }
        }

        switch (necessaryField) {
            case "route":
                System.out.println("Input new value:");
                offer.setRoute(validNull(reader.readLine()));
                break;
            case "departure_time":
                System.out.println("Input new value:");
                offer.setDeparture_time(DATE_FORMAT.parse(reader.readLine()));
                break;
            case "arrival_time":
                System.out.println("Input new value:");
                offer.setArrival_time(DATE_FORMAT.parse(reader.readLine()));
                break;
            case "number_of_seats":
                System.out.println("Input new value:");
                offer.setNumberOfSeats(writeNumberOfSeats(validNull(reader.readLine())));
                break;
        }
    writeOfferInFile(offer);
    }

    private void cleanFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write("");
        fileWriter.close();
    }

    private String writeNumberOfSeats (String number) {
        String numberOfSeats = "";
        for (int k = 1; k < Integer.parseInt(number); k++) {
            numberOfSeats += String.valueOf(k) + ",";
        }
        numberOfSeats += number;
        return numberOfSeats;
    }


    public void ticketBlance(String necessaryOfferId) throws IOException, ParseException {
            File file = new File(FILE_OFFERS);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (s[0].equals(necessaryOfferId) ||
                        s[1].equals(necessaryOfferId)
                        || s[0].equals(necessaryOfferId)
                        || s[1].equals(necessaryOfferId)) {
                    offer.setOfferId(Integer.parseInt(s[0]));
                    offer.setRoute(validNull(s[1]));
                    offer.setDeparture_time(DATE_FORMAT.parse(s[2]));
                    offer.setArrival_time(DATE_FORMAT.parse(s[3]));
                    offer.setNumberOfSeats(validNull(s[4]));
                }
            }
            br.close();
            fr.close();
        System.out.println(offer.getNumber_of_seats());

    }

    public void searchTicketByValue(String necessaryValue) throws IOException, ParseException {
        System.out.println("Available number of seats:");
        ticketBlance(necessaryValue);
    }

    public void buyTicket() throws IOException, ParseException {
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
        ticketBlance(validNull(necessaryId));

        System.out.println("What ticket you want to buy?(number)");
        String number = validNull(reader.readLine());

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
        offer.setNumberOfSeats(numberOfSeats);

        file = new File(FILE_OFFERS);
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        while ((line = br.readLine()) != null) {
            s = line.split(" ");
            if (s[0].equals(necessaryId)) {
                writeOfferInFile(offer);
                continue;
            }
            writeOfferInFile(line);
        }

        TicketHelper.ticket.setName(IdentifyUser.customer.getName());
        TicketHelper.ticket.setCustomerId(IdentifyUser.customer.getCustomerId());
        TicketHelper.ticket.setOfferId(offer.getOfferId());
        TicketHelper.ticket.setRoute(offer.getRoute());
        TicketHelper.ticket.setDeparture_time(offer.getDeparture_time());
        TicketHelper.ticket.setArrical_time(offer.getArrival_time());
        TicketHelper.ticket.setNumber(number);

        br.close();
        fr.close();
        writeFromBuffer();
        cleanFile(FILE_BUFFER_OFFERS);
        }
*/


}



