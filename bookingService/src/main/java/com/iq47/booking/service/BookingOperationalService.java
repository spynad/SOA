package com.iq47.booking.service;

import com.iq47.booking.model.exception.UnableToCancelException;
import com.iq47.booking.model.message.OperationalResponse;
import com.iq47.booking.model.message.OperationalStatusResponse;

import javax.jms.JMSException;

public interface BookingOperationalService {
    OperationalResponse cancelBooking(Long personId) throws JMSException;

    OperationalResponse sellTicket(Long ticketId, Long personId, Integer price) throws JMSException;

    OperationalStatusResponse getOperationStatus(Long id);

    OperationalStatusResponse cancelOperation(Long id) throws UnableToCancelException;
}
