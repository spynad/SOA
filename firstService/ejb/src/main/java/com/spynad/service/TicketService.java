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
    Response addTicket(Ticket body) throws NotFoundException;
    Response deleteTicket(Long ticketId) throws NotFoundException;
    Response getAllTickets(List<String> sort,List<String> filter,Integer page,Integer pageSize) throws NotFoundException;
    Response getAverageTicketDiscount() throws NotFoundException;
    Response getCheaperTicketsByPrice(Integer price) throws NotFoundException;
    Response getMinimalTicketByCreationDate() throws NotFoundException;
    Response getTicketById(Long ticketId) throws NotFoundException;
    Response updateTicket(Ticket body) throws NotFoundException;
    Response bufferTicket(OperationalTicket body);
    Response submitTicket(Operation body);
    Response cancelBufferTicket(Operation body);
}
