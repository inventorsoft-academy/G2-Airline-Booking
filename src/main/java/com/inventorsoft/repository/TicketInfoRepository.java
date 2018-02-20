package com.inventorsoft.repository;

import com.inventorsoft.model.offer.Offer;
import com.inventorsoft.model.ticket.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketInfoRepository {

    List<Ticket> getTickets();

    Offer saveTicket(final Ticket ticket);

    Optional<Offer> findById(final int id);

    boolean updateOffer(final int id, final Offer newOffer);

    boolean removeTicket(final int id);


}
