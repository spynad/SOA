package com.spynad.model;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO implements Serializable {
    private static final long serialVersionUID = -2345676543L;
    private Long id;
    private String name;
    private Coordinates coordinates;

    private Date creationDate;
    private Integer price;
    private Long discount;
    private Boolean refundable;
    private Ticket.TypeEnum typeStr;

    private Long personId;
}
