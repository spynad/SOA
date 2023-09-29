package com.spynad.firstservice.service;

import com.spynad.firstservice.exception.NotFoundException;
import com.spynad.firstservice.model.Ticket;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

public interface TicketService {
    Response addTicket(Ticket body,SecurityContext securityContext) throws NotFoundException;
    Response deleteTicket(Long ticketId,SecurityContext securityContext) throws NotFoundException;
    Response getAllTickets(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getAverageTicketDiscount(SecurityContext securityContext) throws NotFoundException;
    Response getCheaperTicketsByPrice(Integer price,SecurityContext securityContext) throws NotFoundException;
    Response getMinimalTicketByCreationDate(SecurityContext securityContext) throws NotFoundException;
    Response getTicketById(Long ticketId,SecurityContext securityContext) throws NotFoundException;
    Response updateTicket(Ticket body,SecurityContext securityContext) throws NotFoundException;
}
