package com.inventorsoft.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@AllArgsConstructor
@Controller
@RequestMapping(value = "/offers")
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
public class WebOfferController {

    private OfferService offerService;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-kk:mm");

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> getOffers() {
        return ResponseEntity.ok(offerService.getOffers());
    }

    @GetMapping (value = "/forCustomer" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> getOffersForCustomer() {
        return ResponseEntity.ok(offerService.getOffersForCustomer());
    }

    /*@GetMapping(value = "/search", params = "departureCity")
    public ResponseEntity<Offer> getOfferByRoute(@RequestParam String departureCity) {
        return renderOfferByRoute(departureCity);
    }*/

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Offer> getOfferById(@PathVariable int id) {
        System.out.println("you in GetOfferById!!!!");
        return offerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping( value = "/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> searchOffers(@RequestParam(value = "departureCity") String departureCity,
                                                    @RequestParam(value = "departureDate") String departureDate) {
        System.out.println("You want to searchhhhhhhhhhhhhh!");
        System.out.println(departureCity);
        System.out.println(departureDate);
        return ResponseEntity.ok(offerService.searchOffers(departureCity,departureDate));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Offer> createOfferFromUrlEncoded(@ModelAttribute Offer offer) {
        final Offer createdOffer = offerService.saveOffer(offer);
        return renderResponseWithLocation(createdOffer);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Offer> createOfferFromJson(@RequestBody Offer offer) {
        final Offer createdOffer = offerService.saveOffer(offer);
        return renderResponseWithLocation(createdOffer);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity updateOffer(@PathVariable int id,
                                      @RequestBody Offer updates) {
        return offerService.update(id, updates)
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity removeOffer(@PathVariable int id) {
        return offerService.remove(id)
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }



    private ResponseEntity<Offer> renderResponseWithLocation(Offer createdOffer) {
        return ResponseEntity.created(URI.create(String.format("/offers/%d", createdOffer.getId()))).body(createdOffer);
    }


}
