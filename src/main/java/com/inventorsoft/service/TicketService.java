package com.inventorsoft.service;

public interface TicketService {

    Integer getTicketPrice();

    boolean download(String offerId, String customerId);
}
