package com.iq47.booking.service;

import com.iq47.booking.model.data.Person;
import com.iq47.booking.model.data.Ticket;
import com.iq47.booking.model.data.TicketsArray;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.model.exception.CancelOperationException;
import com.iq47.booking.model.message.Operation;
import com.iq47.booking.model.message.OperationalTicket;
import javax.xml.bind.JAXB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class BookingService {

    @Value( "${restService.baseURL}" )
    private String restServiceBaseUrl;

    @Qualifier("getTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("putTemplate")
    @Autowired
    private RestTemplate putRestTemplate;

    private final HttpHeaders headers;

    private final OperationalService operationalService;

    @Autowired
    public BookingService(HttpHeaders headers, OperationalService operationalService) {
        this.headers = headers;
        this.operationalService = operationalService;
    }

    public void sellTicket(Long ticketId, Long personId, Integer price, Long operationId) {
        try {
            String url = String.format("%s/person/%d", restServiceBaseUrl, personId);
            Person person = restTemplate.getForObject(url, Person.class);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
            HttpEntity request = new HttpEntity(headers);
            url = String.format("%s/ticket/%d", restServiceBaseUrl, ticketId);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class,
                    1
            );
            Ticket ticket = JAXB.unmarshal(new StringReader(response.getBody()), Ticket.class);
            try {
                assert ticket != null && person != null;
                sellTicketInternal(ticket, person, price, operationId);
            } catch (CancelOperationException e) {
                performOperationRollback(operationId);
            }
        } catch (Exception e) {
            log.error(String.format("Sell ticket error for params personId=%d, ticketId=%d, price=%d, operationId=%d",
                            personId, ticketId, price, operationId), e);
            Optional<com.iq47.booking.model.entity.Operation> operationOptional = operationalService.getById(operationId);
            com.iq47.booking.model.entity.Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
            operation.setFinishedAt(LocalDateTime.now());
            operation.setStatus(OperationStatus.ERROR);
            operationalService.save(operation);
        }
    }

    @Transactional(rollbackOn = { CancelOperationException.class})
    public void sellTicketInternal(Ticket ticket, Person person, Integer price, Long operationId) throws CancelOperationException {
        String url = String.format("%s/ticket/buffer", restServiceBaseUrl);
        ticket.setPerson(person);
        ticket.setPrice(price);
        StringWriter sw = new StringWriter();
        JAXB.marshal(new OperationalTicket(operationId, ticket), sw);
        HttpEntity<String> request =
                new HttpEntity<>(sw.toString(), headers);
        final ResponseEntity<String> response = putRestTemplate.postForEntity(url, request, String.class);
        finishOperation(operationId);
    }

    @Transactional
    public void finishOperation(Long operationId) throws CancelOperationException {
        Optional<com.iq47.booking.model.entity.Operation> operationOptional = operationalService.getById(operationId);
        com.iq47.booking.model.entity.Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
        if (Objects.requireNonNull(operation.getStatus()) == OperationStatus.REQUESTED_CANCELLATION) {
            throw new CancelOperationException();
        }
        String url = String.format("%s/ticket/buffer/submit", restServiceBaseUrl);
        StringWriter sw = new StringWriter();
        JAXB.marshal(new Operation(operationId), sw);
        HttpEntity<String> request =
                new HttpEntity<>(sw.toString(), headers);
        final ResponseEntity<String> response = putRestTemplate.postForEntity(url, request, String.class);
        operation.setFinishedAt(LocalDateTime.now());
        operation.setStatus(OperationStatus.FINISHED);
        operationalService.save(operation);
    }

    void performOperationRollback(Long operationId) {
        Optional<com.iq47.booking.model.entity.Operation> operationOptional = operationalService.getById(operationId);
        com.iq47.booking.model.entity.Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
        String url = String.format("%s/ticket/buffer/cancel", restServiceBaseUrl);
        StringWriter sw = new StringWriter();
        JAXB.marshal(new Operation(operationId), sw);
        HttpEntity<String> request =
                new HttpEntity<>(sw.toString(), headers);
        final ResponseEntity<String> response = putRestTemplate.postForEntity(url, request, String.class);
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
            Optional<com.iq47.booking.model.entity.Operation> operationOptional = operationalService.getById(operationId);
            com.iq47.booking.model.entity.Operation operation = operationOptional.orElseThrow(IllegalArgumentException::new);
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
            StringWriter sw = new StringWriter();
            JAXB.marshal(new OperationalTicket(operationId, ticket), sw);
            HttpEntity<String> request =
                    new HttpEntity<>(sw.toString(), headers);
            final ResponseEntity<String> response = putRestTemplate.postForEntity(url, request, String.class);
        }
        finishOperation(operationId);
    }
}