package com.iq47.booking.message;

import org.springframework.stereotype.Component;

@Component
public class JMSMessageConverter implements MessageConverter {

    @Override
    public String convertMessage(SellTicketMessage sellTicketMessage) {
        return String.format("{'type':'sell_ticket', 'person_id':'%d', 'ticker_id':'%d', 'price':'%d', 'task_id':'%s', }",
                sellTicketMessage.getPersonId(), sellTicketMessage.getTicketId(), sellTicketMessage.getPrice(), sellTicketMessage.getTaskId());
    }

    @Override
    public String convertMessage(CancelBookingMessage sellTicketMessage) {
        return String.format("{'type':'cancel_booking', 'person_id':'%d',  'task_id':'%s', }",
                sellTicketMessage.getPersonId(), sellTicketMessage.getTaskId());
    }

}
