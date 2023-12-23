package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Operation;
import com.spynad.model.OperationalTicket;
import com.spynad.model.TicketDTO;
import com.spynad.model.message.OperationalTicketResult;
import com.spynad.model.message.Result;
import com.spynad.model.message.TicketListResult;
import com.spynad.model.message.TicketResult;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TicketService {
    TicketResult addTicket(TicketDTO body) throws NotFoundException;
    Result deleteTicket(Long ticketId) throws NotFoundException;
    TicketListResult getAllTickets(List<String> sort,List<String> filter,Integer page,Integer pageSize) throws NotFoundException;
    Result getAverageTicketDiscount() throws NotFoundException;
    Result getCheaperTicketsByPrice(Integer price) throws NotFoundException;
    TicketResult getMinimalTicketByCreationDate() throws NotFoundException;
    TicketResult getTicketById(Long ticketId) throws NotFoundException;
    TicketResult updateTicket(TicketDTO body) throws NotFoundException;
    OperationalTicketResult bufferTicket(OperationalTicket body);
    boolean submitTicket(Operation body);
    boolean cancelBufferTicket(Operation body);
}
