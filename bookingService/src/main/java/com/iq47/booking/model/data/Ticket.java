package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Integer price;
    private Integer discount;
    private Boolean refundable;
    private String type;
    private Person person;

    @Override
    public Object clone() {
        return new Ticket(id, name, coordinates, creationDate, price, discount, refundable, type, person);
    }
}
