/*
package com.spynad.wsservice;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Operation;
import com.spynad.model.OperationalTicket;
import com.spynad.model.Ticket;
import com.spynad.model.message.OperationalTicketResult;
import com.spynad.model.message.Result;
import com.spynad.model.message.TicketListResult;
import com.spynad.model.message.TicketResult;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface TicketSOAPService {
    @WebMethod
    TicketResult addTicket(Ticket body) throws NotFoundException;
    @WebMethod
    Result deleteTicket(Long ticketId) throws NotFoundException;
    @WebMethod
    TicketListResult getAllTickets(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException;
    @WebMethod
    Result getAverageTicketDiscount() throws NotFoundException;
    @WebMethod
    Result getCheaperTicketsByPrice(Integer price) throws NotFoundException;
    @WebMethod
    TicketResult getMinimalTicketByCreationDate() throws NotFoundException;
    @WebMethod
    TicketResult getTicketById(Long ticketId) throws NotFoundException;
    @WebMethod
    TicketResult updateTicket(Ticket body) throws NotFoundException;
    @WebMethod
    OperationalTicketResult bufferTicket(OperationalTicket body);
    @WebMethod
    boolean submitTicket(Operation body);
    @WebMethod
    boolean cancelBufferTicket(Operation body);
}*/
