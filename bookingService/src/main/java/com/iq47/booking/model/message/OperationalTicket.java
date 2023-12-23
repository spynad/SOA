package com.iq47.booking.model.message;

import com.iq47.booking.model.data.Coordinates;
import com.iq47.booking.model.data.Person;
import com.iq47.booking.model.data.Ticket;
import com.iq47.booking.model.data.XmlLocalDateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

@XmlRootElement(name = "OperationalTicket")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationalTicket {
    private long operationId;

    private Long ticketId;

    private String name;

    private Coordinates coordinates;

    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    private LocalDateTime creationDate;

    private Integer price;

    private Integer discount;

    private Boolean refundable;

    private String type;

    private Long personId;

    public OperationalTicket(long operationId, Ticket ticket) {
        this.operationId = operationId;
        this.name = ticket.getName();
        this.ticketId = ticket.getId();
        if(ticket.getPerson() == null)
            this.personId = null;
        else
            this.personId = ticket.getPerson().getId();
        this.coordinates = ticket.getCoordinates();
        this.creationDate = ticket.getCreationDate();
        this.price = ticket.getPrice();
        this.discount = ticket.getDiscount();
        this.refundable = ticket.getRefundable();
        this.type = ticket.getType();
    }
}
