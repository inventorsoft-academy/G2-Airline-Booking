package com.inventorsoft.service;

import com.inventorsoft.repository.TicketInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DefaultTicketService implements TicketService {

    private TicketInfoRepository ticketInfoRepository;

    @Override
    public Integer getTicketPrice() {
        return this.ticketInfoRepository.getTicketPrice();
    }

    @Override
    public boolean download(String offerId, String customerId) {
        return ticketInfoRepository.download(offerId, customerId);
    }

}
