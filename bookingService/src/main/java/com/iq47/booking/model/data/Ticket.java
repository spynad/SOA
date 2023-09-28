package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@XmlRootElement(name = "ticket")
public class Ticket {
    private Long id;
    private String name;
    @XmlElement
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Integer price;
    private Integer discount;
    private Boolean refundable;
    private String type;
    @XmlElement
    private Person person;

    @Override
    public Object clone() {
        return new Ticket(id, name, coordinates, creationDate, price, discount, refundable, type, person);
    }
}
