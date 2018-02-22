package com.inventorsoft.controller;


import com.inventorsoft.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@AllArgsConstructor
@Controller
@RequestMapping(value = "/tickets")
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
public class WebTicketController {

    private TicketService ticketService;

    @GetMapping(value = "/balance" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getTickets() {
        return ResponseEntity.ok(ticketService.getTicketPrice());
    }

}
