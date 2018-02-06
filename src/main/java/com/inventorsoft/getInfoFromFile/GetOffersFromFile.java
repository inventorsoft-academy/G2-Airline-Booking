package com.inventorsoft.getInfoFromFile;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.validator.OfferValidator;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GetOffersFromFile implements GetInfoFromFile {

    private static final String FILE_OFFERS = "resources/offers.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");
    private final OfferValidator offerValidator = new OfferValidator();

    public List<Offer> getInfo() {
        List<Offer> offerList = new ArrayList<>();
        File file = new File(FILE_OFFERS);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (offerValidator.validateOfferId(s[0]) && offerValidator.validateForUniqueOfferId(s[0])
                        && offerValidator.validateRoute(s[1])
                        && offerValidator.validateDate(s[2]) && offerValidator.compareToCurrentDate(s[2])
                        && offerValidator.validateDate(s[3])
                        && offerValidator.validateNumberOfSeats(s[4])
                        && offerValidator.validatePrice(Integer.parseInt(s[5]))) {
                    Offer offer = new Offer();
                    offer.setId(Integer.parseInt(s[0]));
                    offer.setRoute((s[1]));
                    offer.setNumberOfSeats(s[4]);
                    offer.setPrice(Integer.parseInt(s[5]));
                    try {
                        offer.setDepartureDate(DATE_FORMAT.parse(s[2]));
                        offer.setArrivalDate(DATE_FORMAT.parse(s[3]));
                    } catch (ParseException e) {
                        System.out.println("Problem in GetInfoFromFile method getOffersFromFile() ");
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
}
