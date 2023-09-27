package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.Ticket;

public interface TicketRepository {
    Ticket getTicketById(Long id);
    Ticket saveTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
}
