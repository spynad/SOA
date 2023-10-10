package com.iq47.booking.service;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.data.Person;
import com.iq47.booking.model.data.Ticket;
import com.iq47.booking.model.data.TicketsArray;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.model.exception.CancelOperationException;
import com.iq47.booking.model.message.OperationRequest;
import com.iq47.booking.model.message.OperationalTicketRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class BookingService {

    @Value( "${restService.baseURL}" )
    private String restServiceBaseUrl;

    private final RestTemplate restTemplate;

    private final HttpHeaders headers;

    private final OperationalService operationalService;

    @Autowired
    public BookingService(RestTemplate restTemplate, HttpHeaders headers, OperationalService operationalService) {
        this.restTemplate = restTemplate;
        this.headers = headers;
        this.operationalService = operationalService;
    }

    public void sellTicket(Long ticketId, Long personId, Integer price, Long operationId) {
        try {
            String url = String.format("%s/ticket/%d", restServiceBaseUrl, ticketId);
            Ticket ticket = restTemplate.getForObject(url, Ticket.class);
            url = String.format("%s/person/%d", restServiceBaseUrl, personId);
            Person person = restTemplate.getForObject(url, Person.class);
            try {
                assert ticket != null && person != null;
                sellTicketInternal(ticket, person, price, operationId);
            } catch (CancelOperationException e) {
                performOperationRollback(operationId);
            }
        } catch (Exception e) {
            log.error(String.format("Sell ticket error for params personId=%d, ticketId=%d, price=%d, operationId=%d",
                            personId, ticketId, price, operationId), e);
            Optional<Operation> operationOptional = operationalService.getById(operationId);
            Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
            operation.setFinishedAt(LocalDateTime.now());
            operation.setStatus(OperationStatus.ERROR);
            operationalService.save(operation);
        }
    }

    @Transactional(rollbackOn = { CancelOperationException.class})
    public void sellTicketInternal(Ticket ticket, Person person, Integer price, Long operationId) throws CancelOperationException {
        String url = String.format("%s/ticket/buffer", restServiceBaseUrl);
        Ticket ticketInternal = (Ticket) ticket.clone();
        ticketInternal.setPerson(person);
        ticketInternal.setPrice(price);
        HttpEntity<OperationalTicketRequest> request =
                new HttpEntity<>(new OperationalTicketRequest(operationId, ticket), headers);
        restTemplate.postForObject(url, request, OperationalTicketRequest.class);
        finishOperation(operationId);
    }

    @Transactional
    public void finishOperation(Long operationId) throws CancelOperationException {
        Optional<Operation> operationOptional = operationalService.getById(operationId);
        Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
        if (Objects.requireNonNull(operation.getStatus()) == OperationStatus.REQUESTED_CANCELLATION) {
            throw new CancelOperationException();
        }
        String url = String.format("%s/ticket/buffer/submit", restServiceBaseUrl);
        HttpEntity<OperationRequest> request =
                new HttpEntity<>(new OperationRequest(operationId), headers);
        restTemplate.postForObject(url, request, OperationRequest.class);
        operation.setFinishedAt(LocalDateTime.now());
        operation.setStatus(OperationStatus.FINISHED);
        operationalService.save(operation);
    }

    void performOperationRollback(Long operationId) {
        Optional<Operation> operationOptional = operationalService.getById(operationId);
        Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
        String url = String.format("%s/ticket/buffer/cancel", restServiceBaseUrl);
        HttpEntity<OperationRequest> request =
                new HttpEntity<>(new OperationRequest(operationId), headers);
        restTemplate.postForObject(url, request, OperationRequest.class);
        operation.setFinishedAt(LocalDateTime.now());
        operation.setStatus(OperationStatus.CANCELLED);
        operationalService.save(operation);
    }

    public void cancelBooking(Long personId, Long operationId) {
        try {
            String params = String.format("?filter=person.id[eq]=%d", personId);
            String url = String.format("%s/ticket%s", restServiceBaseUrl, params);
            TicketsArray tickets = restTemplate.getForObject(url, TicketsArray.class);
            try {
                assert tickets != null;
                cancelBookingInternal(tickets, operationId);

            } catch (CancelOperationException e) {
                performOperationRollback(operationId);
            }
        } catch (Exception e) {
            log.error(String.format("Cancel booking error for params personId=%d, operationId=%d",
                    personId, operationId), e);
            Optional<Operation> operationOptional = operationalService.getById(operationId);
            Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
            operation.setFinishedAt(LocalDateTime.now());
            operation.setStatus(OperationStatus.ERROR);
            operationalService.save(operation);
        }
    }

    @Transactional
    public void cancelBookingInternal(TicketsArray tickets, Long operationId) throws CancelOperationException {
        String url = String.format("%s/ticket/buffer", restServiceBaseUrl);
        for (Ticket ticket: tickets.getTickets()) {
            ticket.setPerson(null);
            ticket.setPrice(1);
            HttpEntity<OperationalTicketRequest> request =
                    new HttpEntity<>(new OperationalTicketRequest(operationId, ticket), headers);
            restTemplate.postForObject(url, request, OperationalTicketRequest.class);
        }
        finishOperation(operationId);
    }
}