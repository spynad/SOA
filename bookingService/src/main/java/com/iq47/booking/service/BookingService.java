package com.iq47.booking.service;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.Ticket;
import com.iq47.booking.model.exception.CancelOperationException;
import com.iq47.booking.model.exception.TimeOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Value( "${restService.baseURL}" )
    private String restServiceBaseUrl;

    private final RestTemplate restTemplate;

    private final OperationalService operationalService;

    @Autowired
    public BookingService(RestTemplate restTemplate, OperationalService operationalService) {
        this.restTemplate = restTemplate;
        this.operationalService = operationalService;
    }

    public void sellTicket(Long ticketId, Long personId, Integer price, Long operationId) {
        String params = String.format("?filter=id[eq]=%d", ticketId);
        String url = String.format("%s/ticket%s", restServiceBaseUrl, params);
        Ticket ticket = restTemplate.getForObject(url, Ticket.class);
        try {
            assert ticket != null;
            sellTicketInternal(ticket, personId, price, operationId);
        } catch (CancelOperationException | TimeOutException e) {
            sellTicketRollback(ticket);
        }
    }

    @Transactional(rollbackOn = { CancelOperationException.class, TimeOutException.class })
    public void sellTicketInternal(Ticket ticket, Long personId, Integer price, Long operationId) throws CancelOperationException, TimeOutException {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        Ticket ticketInternal = (Ticket) ticket.clone();
        ticketInternal.setPersonId(personId);
        ticketInternal.setPrice(price);
        restTemplate.put(url, ticket);
        finishOperation(operationId);
    }

    @Transactional
    public void finishOperation(Long operationId) throws CancelOperationException, TimeOutException {
        Optional<Operation> operationOptional = operationalService.getById(operationId);
        Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
        switch (operation.getStatus()) {
            case CANCELLED: {
                throw new CancelOperationException();
            }
            case TIMED_OUT: {
                throw new TimeOutException();
            }
        }
    }

    void sellTicketRollback(Ticket ticket) {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        restTemplate.put(url, ticket);
    }

    public void cancelBooking(Long personId, Long operationId) {
        String params = String.format("?filter=person\\.id[eq]=%d", personId);
        String url = String.format("%s/ticket%s", restServiceBaseUrl, params);
        List<Ticket> tickets = (List<Ticket>) restTemplate.getForObject(url, List.class);
        try {
            assert tickets != null;
            cancelBookingInternal(tickets, personId, operationId);
        } catch (CancelOperationException | TimeOutException e) {
            sellTicketsRollback(tickets);
        }
    }

    void sellTicketsRollback(List<Ticket> tickets) {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        for (Ticket ticket: tickets) {
            restTemplate.put(url, ticket);
        }
    }

    @Transactional
    public void cancelBookingInternal(List<Ticket> tickets, Long personId, Long operationId) throws CancelOperationException, TimeOutException {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        for (Ticket ticket: tickets) {
            Ticket ticketInternal = (Ticket) ticket.clone();
            ticketInternal.setPersonId(null);
            ticketInternal.setPrice(null);
            restTemplate.put(url, ticket);
        }
        finishOperation(operationId);
    }
}