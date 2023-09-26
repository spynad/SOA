package com.iq47.booking.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Ticket {
    private Long id;
    private String name;
    private Long coordinatesId;
    private LocalDateTime creationDate;
    private Integer price;
    private Integer discount;
    private Boolean refundable;
    private String type;
    private Long personId;

    @Override
    public Object clone() {
        return new Ticket(id, name, coordinatesId, creationDate, price, discount, refundable, type, personId);
    }
}
