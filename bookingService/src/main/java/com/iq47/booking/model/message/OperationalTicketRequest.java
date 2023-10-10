package com.iq47.booking.model.message;

import com.iq47.booking.model.data.Coordinates;
import com.iq47.booking.model.data.Person;
import com.iq47.booking.model.data.Ticket;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@XmlRootElement(name = "OperationalTicket")
@AllArgsConstructor
@Data
public class OperationalTicketRequest {
    private long operationId;
    @XmlElement
    private Ticket ticket;
}
