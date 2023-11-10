package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Operation;
import com.spynad.model.OperationalTicket;
import com.spynad.model.Ticket;
import jakarta.ejb.Remote;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Remote
public interface TicketService {
    Response addTicket(Ticket body,SecurityContext securityContext) throws NotFoundException;
    Response deleteTicket(Long ticketId,SecurityContext securityContext) throws NotFoundException;
    Response getAllTickets(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getAverageTicketDiscount(SecurityContext securityContext) throws NotFoundException;
    Response getCheaperTicketsByPrice(Integer price,SecurityContext securityContext) throws NotFoundException;
    Response getMinimalTicketByCreationDate(SecurityContext securityContext) throws NotFoundException;
    Response getTicketById(Long ticketId,SecurityContext securityContext) throws NotFoundException;
    Response updateTicket(Ticket body,SecurityContext securityContext) throws NotFoundException;
    Response bufferTicket(OperationalTicket body, SecurityContext securityContext);
    Response submitTicket(Operation body, SecurityContext securityContext);
    Response cancelBufferTicket(Operation body, SecurityContext securityContext);
}
