package com.inventorsoft.downloadInfo;

import com.inventorsoft.model.offer.Offer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DownloadOffers implements DownloadInfo {

    private static final String FILE_OFFERS = "src/main/resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    public static List<Offer> getInfo() {
        List<Offer> offerList = new ArrayList<>();
        File file = new File(FILE_OFFERS);
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return offerList;
    }
}
