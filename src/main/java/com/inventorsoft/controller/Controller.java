package com.inventorsoft.controller;


import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Spring MVC com.inventorsoft.controller which shows different Spring web support concepts.
 * This com.inventorsoft.controller shows mixed url definition, request parameter handling and rendering
 * different types of response
 */
@AllArgsConstructor
@org.springframework.stereotype.Controller
@RequestMapping(value = "/offers")
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
public class Controller {

    private OfferService offerService;

    /**
     * Simple mapping example
     *
     * @return returns all song infos
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> getOffers() {
        return ResponseEntity.ok(offerService.getOffers());
    }

    @GetMapping(value = "/search", params = "route")
    public ResponseEntity<Offer> getOfferByRoute(@RequestParam String route) {
        return renderOfferByRoute(route);
    }

   @GetMapping(value = "/search", headers = "Offer-Route")
    public ResponseEntity<Offer> getSongByNameInHeader(@RequestHeader("Offer-Route") String route) {
        return renderOfferByRoute(route);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Offer> getSongById(@PathVariable int id) {
        return offerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Offer> createOfferFromUrlEncoded(@ModelAttribute Offer offer) {
        final Offer createdOffer = offerService.saveOffer(offer);
        return renderResponseWithLocation(createdOffer);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Offer> createSongFromJson(@RequestBody Offer offer) {
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


    private ResponseEntity<Offer> renderOfferByRoute(@RequestParam String route) {
        return offerService.findByRoute(route)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<Offer> renderResponseWithLocation(Offer createdOffer) {
        return ResponseEntity.created(URI.create(String.format("/offers/%d", createdOffer.getId()))).body(createdOffer);
    }

}
