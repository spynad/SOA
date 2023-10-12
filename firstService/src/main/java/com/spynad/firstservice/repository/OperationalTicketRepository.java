package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.OperationalTicket;

public interface OperationalTicketRepository {
    OperationalTicket saveOperationalTicket(OperationalTicket ticket);
}
