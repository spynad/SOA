package com.iq47.booking.service;

import com.iq47.booking.model.exception.UnableToCancelException;
import com.iq47.booking.model.message.OperationalResponse;
import com.iq47.booking.model.message.OperationalStatusResponse;

public interface BookingOperationalService {
    OperationalResponse cancelBooking(Long personId);

    OperationalResponse sellTicket(Long ticketId, Long personId, Integer price);

    OperationalStatusResponse getOperationStatus(Long id);

    OperationalStatusResponse cancelOperation(Long id) throws UnableToCancelException;
}
