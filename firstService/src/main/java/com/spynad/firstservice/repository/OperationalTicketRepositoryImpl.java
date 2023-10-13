package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.OperationalTicket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class OperationalTicketRepositoryImpl implements OperationalTicketRepository{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public OperationalTicket saveOperationalTicket(OperationalTicket ticket) {
        entityManager.persist(ticket);
        return ticket;
    }

    @Override
    public OperationalTicket updateOperationalTicket(OperationalTicket ticket) {
        return entityManager.merge(ticket);
    }

    @Override
    public List<OperationalTicket> getPendingOperationalTickets(Long id) {
        List<OperationalTicket> tickets = entityManager
                .createQuery("select ticket from OperationalTicket ticket where status=StatusEnum.PENDING and operationId=:id")
                .setParameter("id", id)
                .getResultList();
        return tickets;
    }

    @Override
    public List<OperationalTicket> getOperationalTicketsByOperationId(Long id) {
        List<OperationalTicket> tickets = entityManager.createQuery("select ticket from OperationalTicket ticket where operationId=:id")
                .setParameter("id", id)
                .getResultList();
        return tickets;
    }
}
