package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class TicketRepositoryImpl implements TicketRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Ticket getTicketById(Long id) {
        return entityManager.find(Ticket.class, id);
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        entityManager.persist(ticket);
        return ticket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return entityManager.merge(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        entityManager.remove(ticket);
    }
}
