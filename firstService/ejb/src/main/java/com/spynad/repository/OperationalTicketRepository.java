package com.spynad.repository;

import com.spynad.model.OperationalTicket;

import java.util.List;

public interface OperationalTicketRepository {
    OperationalTicket saveOperationalTicket(OperationalTicket ticket);
    OperationalTicket updateOperationalTicket(OperationalTicket ticket);
    List<OperationalTicket> getPendingOperationalTickets(Long id);
    List<OperationalTicket> getOperationalTicketsByOperationId(Long id);
}
