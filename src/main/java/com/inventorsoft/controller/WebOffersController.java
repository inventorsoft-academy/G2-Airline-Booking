package com.inventorsoft.controller;


import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.service.OfferService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@AllArgsConstructor
@NoArgsConstructor
@Controller
@RequestMapping(value = "/offers")
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
public class WebOffersController {

    private OfferService offerService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> getOffers() {
        return ResponseEntity.ok(offerService.getOffers());
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
                : new ResponseEntity(HttpStatus.NOT_FOUND);
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
