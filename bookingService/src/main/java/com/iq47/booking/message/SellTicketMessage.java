package com.iq47.booking.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellTicketMessage implements OperationalMessage {
    Long taskId;
    Long personId;
    Long ticketId;
    Integer price;

    public SellTicketMessage(Long personId, Long ticketId, Integer price) {
        this.personId = personId;
        this.ticketId = ticketId;
        this.price = price;
    }
}
