package com.iq47.booking.service;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.data.Person;
import com.iq47.booking.model.data.Ticket;
import com.iq47.booking.model.data.TicketsArray;
import com.iq47.booking.model.exception.CancelOperationException;
import com.iq47.booking.model.exception.TimeOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
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
        url = String.format("%s/person", restServiceBaseUrl);
        Person person = restTemplate.getForObject(url, Person.class);
        try {
            assert ticket != null;
            sellTicketInternal(ticket, person, price, operationId);
        } catch (CancelOperationException | TimeOutException e) {
            sellTicketRollback(ticket);
        }
    }

    @Transactional(rollbackOn = { CancelOperationException.class, TimeOutException.class })
    public void sellTicketInternal(Ticket ticket, Person person, Integer price, Long operationId) throws CancelOperationException, TimeOutException {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        Ticket ticketInternal = (Ticket) ticket.clone();
        ticketInternal.setPerson(person);
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

    public void cancelBooking(Long personId, Long operationId) throws TimeOutException {
        String params = String.format("?filter=person\\.id[eq]=%d", personId);
        String url = String.format("%s/ticket%s", restServiceBaseUrl, params);
        TicketsArray tickets = restTemplate.getForObject(url, TicketsArray.class);
        try {
            assert tickets != null;
            cancelBookingInternal(tickets, operationId);
        } catch (CancelOperationException e) {
            cancelBookingRollback(tickets);
        }
    }

    void cancelBookingRollback(TicketsArray tickets) {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        for (Ticket ticket: tickets.getTickets()) {
            restTemplate.put(url, ticket);
        }
    }

    @Transactional
    public void cancelBookingInternal(TicketsArray tickets, Long operationId) throws CancelOperationException, TimeOutException {
        String url = String.format("%s/ticket", restServiceBaseUrl);
        for (Ticket ticket: tickets.getTickets()) {
            Ticket ticketInternal = (Ticket) ticket.clone();
            ticketInternal.setPerson(null);
            ticketInternal.setPrice(1);
            restTemplate.put(url, ticket);
        }
        finishOperation(operationId);
    }
}