package com.iq47.booking.message;

public interface MessageConverter {
    String convertMessage(SellTicketMessage sellTicketMessage);

    String convertMessage(CancelBookingMessage sellTicketMessage);
}
