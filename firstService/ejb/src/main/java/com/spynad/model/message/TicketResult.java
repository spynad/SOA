package com.spynad.model.message;

import com.spynad.model.Person;
import com.spynad.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -983579L;
    private String message;
    private Ticket result;
    private int code;
}
