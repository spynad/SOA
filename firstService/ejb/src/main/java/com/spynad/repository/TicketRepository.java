package com.spynad.repository;

import com.spynad.model.*;

import java.util.List;

public interface TicketRepository {
    Page getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size);
    Ticket getTicketById(Long id);

    List<Ticket> getAllTickets();
    Ticket saveTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
}
