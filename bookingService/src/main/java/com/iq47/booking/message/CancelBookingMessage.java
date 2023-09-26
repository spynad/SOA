package com.iq47.booking.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBookingMessage implements OperationalMessage {
    Long taskId;
    Long personId;

    public CancelBookingMessage(Long personId) {
        this.personId = personId;
    }
}
