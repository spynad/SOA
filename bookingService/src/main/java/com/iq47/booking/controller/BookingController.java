package com.iq47.booking.controller;

import com.iq47.booking.model.exception.UnableToCancelException;
import com.iq47.booking.model.message.OperationalResponse;
import com.iq47.booking.model.message.OperationalStatusResponse;
import com.iq47.booking.service.BookingOperationalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.util.Arrays;
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
    @PostMapping("/person/{personId}/cancel")
    public ResponseEntity<OperationalResponse> cancelBooking(@PathVariable("personId") Long personId) {
        try {
            OperationalResponse response = bookingOperationalService.cancelBooking(personId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (JMSException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /booking/person/cancel/{id}
    @GetMapping("/person/cancel/{id}")
    public ResponseEntity<OperationalStatusResponse> getCancelBookingStatus(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.getOperationStatus(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /booking/person/cancel/{id}
    @DeleteMapping("/person/cancel/{id}")
    public ResponseEntity<OperationalStatusResponse> cancelBookingCancellation(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.cancelOperation(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnableToCancelException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to sell a ticket to a person
    @PostMapping("/sell/{ticketId}/{personId}/{price}")
    public ResponseEntity<OperationalResponse> sellTicket(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("personId") Long personId,
            @PathVariable("price") Integer price) {
        try {
            OperationalResponse response = bookingOperationalService.sellTicket(ticketId, personId, price);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (JMSException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get status of a ticket sell to a person
    @GetMapping("/sell/{id}")
    public ResponseEntity<OperationalStatusResponse> sellTicketStatus(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.getOperationStatus(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to cancel an operation of a ticket sell to a person
    @DeleteMapping("/sell/{id}")
    public ResponseEntity<OperationalStatusResponse> sellTicketCancel(@PathVariable("id") Long id) {
        try {
            OperationalStatusResponse response = bookingOperationalService.cancelOperation(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnableToCancelException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}