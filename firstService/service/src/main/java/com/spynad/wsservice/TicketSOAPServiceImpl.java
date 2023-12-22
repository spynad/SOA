/*
package com.spynad.wsservice;

import com.spynad.config.JNDIConfig;
import com.spynad.exception.NotFoundException;
import com.spynad.model.Operation;
import com.spynad.model.OperationalTicket;
import com.spynad.model.Ticket;
import com.spynad.model.message.OperationalTicketResult;
import com.spynad.model.message.Result;
import com.spynad.model.message.TicketListResult;
import com.spynad.model.message.TicketResult;
import com.spynad.service.TicketService;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService(endpointInterface = "com.spynad.wsservice.TicketSOAPService")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public class TicketSOAPServiceImpl implements TicketSOAPService {

    TicketService service = JNDIConfig.ticketService();
    @Override
    public TicketResult addTicket(Ticket body) throws NotFoundException {
        return service.addTicket(body);
    }

    @Override
    public Result deleteTicket(Long ticketId) throws NotFoundException {
        return service.deleteTicket(ticketId);
    }

    @Override
    public TicketListResult getAllTickets(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException {
        return service.getAllTickets(sort, filter, page, pageSize);
    }

    @Override
    public Result getAverageTicketDiscount() throws NotFoundException {
        return service.getAverageTicketDiscount();
    }

    @Override
    public Result getCheaperTicketsByPrice(Integer price) throws NotFoundException {
        return service.getCheaperTicketsByPrice(price);
    }

    @Override
    public TicketResult getMinimalTicketByCreationDate() throws NotFoundException {
        return service.getMinimalTicketByCreationDate();
    }

    @Override
    public TicketResult getTicketById(Long ticketId) throws NotFoundException {
        return service.getTicketById(ticketId);
    }

    @Override
    public TicketResult updateTicket(Ticket body) throws NotFoundException {
        return service.updateTicket(body);
    }

    @Override
    public OperationalTicketResult bufferTicket(OperationalTicket body) {
        return service.bufferTicket(body);
    }

    @Override
    public boolean submitTicket(Operation body) {
        return service.submitTicket(body);
    }

    @Override
    public boolean cancelBufferTicket(Operation body) {
        return service.cancelBufferTicket(body);
    }
}
*/
