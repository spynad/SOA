package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.OperationalTicket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class OperationalTicketRepositoryImpl implements OperationalTicketRepository{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public OperationalTicket saveOperationalTicket(OperationalTicket ticket) {
        entityManager.persist(ticket);
        return ticket;
    }
}
