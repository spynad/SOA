package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.Filter;
import com.spynad.firstservice.model.Sort;
import com.spynad.firstservice.model.Ticket;
import com.spynad.firstservice.model.TicketsArray;

import java.util.List;

public interface TicketRepository {
    TicketsArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size);
    Ticket getTicketById(Long id);

    List<Ticket> getAllTickets();
    Ticket saveTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
}
