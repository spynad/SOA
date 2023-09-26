package com.iq47.booking.service;

import com.iq47.booking.message.CancelBookingMessage;
import com.iq47.booking.message.SellTicketMessage;
import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.model.exception.UnableToCancelException;
import com.iq47.booking.model.message.OperationalResponse;
import com.iq47.booking.model.message.OperationalStatusResponse;
import com.iq47.booking.producer.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class BookingOperationalServiceImpl implements BookingOperationalService {

    private final BookingService bookingService;

    private final OperationalService operationalService;

    private final MessageSender messageSender;


    @Autowired
    public BookingOperationalServiceImpl(BookingService bookingService, OperationalService operationalService, MessageSender messageSender) {
        this.bookingService = bookingService;
        this.operationalService = operationalService;
        this.messageSender = messageSender;
    }


    @Override
    public OperationalResponse cancelBooking(Long personId) throws JMSException {
        Operation operation = messageSender.sendMessage(new CancelBookingMessage(personId));
        return new OperationalResponse(operation.getId(), operation.getStart());
    }

    @Override
    public OperationalStatusResponse getOperationStatus(Long id) {
        Operation operation = operationalService.getById(id).orElseThrow(NoSuchElementException::new);
        return new OperationalStatusResponse(id, operation.getFinishedAt(), operation.getStatus().toString());
    }

    @Override
    public OperationalStatusResponse cancelOperation(Long id) throws UnableToCancelException {
        Operation operation = cancelOperationInternal(id);
        return new OperationalStatusResponse(id, operation.getFinishedAt(), operation.getStatus().toString());
    }

    @Transactional
    public Operation cancelOperationInternal(Long id) throws UnableToCancelException {
        Operation operation = operationalService.getById(id).orElseThrow(NoSuchElementException::new);
        if (!operation.getStatus().equals(OperationStatus.EXECUTION)
                && !operation.getStatus().equals(OperationStatus.PENDING)
                && !operation.getStatus().equals(OperationStatus.TIMED_OUT)) {
            operation.setStatus(OperationStatus.CANCELLED);
            operation.setFinishedAt(LocalDateTime.now());
            operationalService.save(operation);
            return operation;
        } else {
            throw new UnableToCancelException();
        }
    }

    @Override
    public OperationalResponse sellTicket(Long ticketId, Long personId, Integer price) throws JMSException {
        Operation operation = messageSender.sendMessage(new SellTicketMessage(ticketId, personId, price));
        return new OperationalResponse(operation.getId(), operation.getStart());
    }
}
