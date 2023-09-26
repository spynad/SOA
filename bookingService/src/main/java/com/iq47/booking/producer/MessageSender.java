package com.iq47.booking.producer;

import com.iq47.booking.message.CancelBookingMessage;
import com.iq47.booking.message.SellTicketMessage;
import com.iq47.booking.model.entity.Operation;

import javax.jms.JMSException;

public interface MessageSender {
    Operation sendMessage(SellTicketMessage reportMessage) throws JMSException;

    Operation sendMessage(CancelBookingMessage reportMessage) throws JMSException;

    Operation sendOperationalMessage(Operation operation, String text) throws JMSException;
}
