//package com.inventorsoft.model.offer;
//
//import com.IO.Validator;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.text.ParseException;
//
//public class IdentifyPath {
//    private static final OfferOperations offerOperations = new OfferOperations();
//    private static final Validator validator = new Validator();
//    private String answer;
//
//    public IdentifyPath() throws IOException, ParseException {
//        identifyOperation();
//        implementPath();
//    }
//
//    private void implementPath() throws IOException, ParseException {
//        switch (answer) {
//            case "c":
//                offerOperations.createOffer();
//                break;
//            case "e":
//                offerOperations.editOffer();
//                break;
//            case "d":
//                offerOperations.delete();
//                break;
//            case "i":
//                offerOperations.importAllOffers();
//                break;
//        }
//    }
//
//    private void identifyOperation() throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Create edit delete or import all offers?(c/e/d/i)");
//        while (true) {
//            answer = validator.validNull(reader.readLine());
//            if (answer.equals("c") || answer.equals("e") || answer.equals("d") || answer.equals("i")) break;
//        }
//    }
//}
//
//
