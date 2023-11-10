package com.spynad.model.message;

import com.spynad.model.PersonArray;
import com.spynad.model.TicketsArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketListResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -913478L;
    private String message;
    private TicketsArray result;
    private int status;
}
