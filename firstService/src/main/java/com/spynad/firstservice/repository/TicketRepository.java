package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.*;

import java.util.List;

public interface TicketRepository {
    TicketsArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size);
    Ticket getTicketById(Long id);
    Ticket saveTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
}
