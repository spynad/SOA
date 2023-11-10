package com.spynad.repository;

import com.spynad.model.Filter;
import com.spynad.model.Sort;
import com.spynad.model.Ticket;
import com.spynad.model.TicketsArray;

import java.util.List;

public interface TicketRepository {
    TicketsArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size);
    Ticket getTicketById(Long id);

    List<Ticket> getAllTickets();
    Ticket saveTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
}
