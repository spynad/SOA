package com.spynad.model.message;

import com.spynad.model.OperationalTicket;
import com.spynad.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationalTicketResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -983479L;
    private String message;
    private OperationalTicket result;
    private int code;
}

