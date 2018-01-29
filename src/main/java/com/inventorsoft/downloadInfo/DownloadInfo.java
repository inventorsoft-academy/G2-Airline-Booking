package com.inventorsoft.downloadInfo;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;
import com.inventorsoft.model.user.Admin;
import com.inventorsoft.model.user.Customer;
import com.inventorsoft.model.user.User;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DownloadInfo {

    private static final String ADMINS_FILE = "src/main/resources/admins.txt";
    private static final String CUSTOMERS_FILE = "src/main/resources/customers.txt";
    private static final String FILE_OFFERS = "src/com/TextTest/offers.txt";
    private static final String FILE_TICKETS = "src/com/TextTest/tickets.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");


    /**
     *
     * @param userType
     * @return List<Customer> or List<Admin>
     * @throws IOException
     */
    public List<? extends User> getUsersFromFile(String userType) throws IOException {

        if (userType.equals("1")) {
            List<Customer> customerList = new ArrayList<>();
            File file = new File(CUSTOMERS_FILE);
            try (FileReader fr = new FileReader(file)) {
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    Customer customer = new Customer();
                    String[] s = line.split(" ");
                    customer.setId(Integer.parseInt(s[0]));
                    customer.setLogin(s[1]);
                    customer.setPassword(s[2]);
                    customer.setEmail(s[3]);
                    customer.setName(s[4]);
                    customerList.add(customer);
                }
                br.close();
                fr.close();
            }
            return customerList;
        }

        if (userType.equals("2")) {
            List<Admin> adminList = new ArrayList<>();
            File file = new File(ADMINS_FILE);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Admin admin = new Admin();
                String[] s = line.split(" ");
                admin.setId(Integer.parseInt(s[0]));
                admin.setLogin(s[1]);
                admin.setPassword(s[2]);
                admin.setEmail(s[3]);
                adminList.add(admin);
            }
            br.close();
            fr.close();
            return adminList;
        }
        return null;
    }

    public List<Ticket> getTicketsFromFile() throws IOException {
        List<Ticket> ticketList = new ArrayList<>();
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            Ticket ticket = new Ticket();
            ticket.setName((s[0]));
            ticket.setCustomerId(Integer.parseInt(s[1]));
            ticket.setOfferId(Integer.parseInt(s[2]));
            ticket.setRoute((s[3]));
            try {
                ticket.setDeparture_time(DATE_FORMAT.parse(s[4]));
                ticket.setArrival_time(DATE_FORMAT.parse(s[5]));
            } catch (ParseException e) {
                System.out.println("Problem in DownloadInfo method getTicketsFromFile()");
                e.printStackTrace();
            }
            ticket.setNumber(s[6]);
            ticket.setPrice(Integer.parseInt(s[7]));
            ticketList.add(ticket);
        }
        br.close();
        fr.close();
        return ticketList;
    }

    public List<Offer> getOffersFromFile() throws IOException {
        List<Offer> offerList = new ArrayList<>();
        File file = new File(FILE_OFFERS);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
                Offer offer = new Offer();
                offer.setId(Integer.parseInt(s[0]));
                offer.setRoute((s[1]));
            try {
                offer.setDepartureDate(DATE_FORMAT.parse(s[2]));
                offer.setArrivalDate(DATE_FORMAT.parse(s[3]));
            } catch (ParseException e) {
                System.out.println("Problem in DownloadInfo method getOffersFromFile() ");
                e.printStackTrace();
            }
            offer.setNumberOfSeats(s[4]);
                offer.setPrice(Integer.parseInt(s[5]));
                offerList.add(offer);
        }
        br.close();
        fr.close();
        return offerList;
    }
}

