package com.iq47.booking.controller;

import com.iq47.booking.model.exception.UnableToCancelException;
import com.iq47.booking.model.message.OperationalResponse;
import com.iq47.booking.model.message.OperationalStatusResponse;
import com.iq47.booking.service.BookingOperationalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/booking")
@Slf4j
public class BookingController {


    private final BookingOperationalService bookingOperationalService;

    public BookingController(BookingOperationalService bookingOperationalService) {
        this.bookingOperationalService = bookingOperationalService;
    }

    // POST /booking/person/{person-id}/cancel
    @PostMapping(value = "/person/{personId}/cancel", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OperationalResponse> cancelBooking(@PathVariable("personId") Long personId) {
        OperationalResponse response = bookingOperationalService.cancelBooking(personId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /booking/person/cancel/{id}
    @GetMapping(value = "/person/cancel/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OperationalStatusResponse> getCancelBookingStatus(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.getOperationStatus(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /booking/person/cancel/{id}
    @DeleteMapping(value = "/person/cancel/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OperationalStatusResponse> cancelBookingCancellation(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.cancelOperation(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnableToCancelException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to sell a ticket to a person
    @PostMapping(value = "/sell/{ticketId}/{personId}/{price}", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OperationalResponse> sellTicket(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("personId") Long personId,
            @PathVariable("price") Integer price) {
            OperationalResponse response = bookingOperationalService.sellTicket(ticketId, personId, price);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint to get status of a ticket sell to a person
    @GetMapping(value = "/sell/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OperationalStatusResponse> sellTicketStatus(@PathVariable("id") Long id) {
        try {
            com.iq47.booking.model.message.OperationalStatusResponse response = bookingOperationalService.getOperationStatus(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to cancel an operation of a ticket sell to a person
    @DeleteMapping(value = "/sell/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OperationalStatusResponse> sellTicketCancel(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.cancelOperation(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnableToCancelException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}