package com.spynad.wsservice;

import com.spynad.config.JNDIConfig;
import com.spynad.exception.NotFoundException;
import com.spynad.model.*;
import com.spynad.model.message.*;
import com.spynad.service.PersonService;
import com.spynad.service.TicketService;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@WebService(endpointInterface = "com.spynad.wsservice.SOAPService")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public class SOAPServiceImpl implements SOAPService{
    PersonService personService = JNDIConfig.personService();
    @Override
    public PersonResult addPerson(Person body) throws NotFoundException {
        return personService.addPerson(body);
    }

    @Override
    public Result deletePerson(Long personId) throws NotFoundException {
        return personService.deletePerson(personId);
    }

    @Override
    public PersonListResult getAllPerson(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException {
        return personService.getAllPerson(sort, filter, page, pageSize);
    }

    @Override
    public PersonResult getPersonById(Long personId) throws NotFoundException {
        return personService.getPersonById(personId);
    }

    @Override
    public PersonResult updatePerson(Person body) throws NotFoundException {
        return personService.updatePerson(body);
    }
    TicketService ticketService = JNDIConfig.ticketService();
    @Override
    public TicketResult addTicket(TicketDTO body) throws NotFoundException {
        return ticketService.addTicket(body);
    }

    @Override
    public Result deleteTicket(Long ticketId) throws NotFoundException {
        return ticketService.deleteTicket(ticketId);
    }

    @Override
    public TicketListResult getAllTickets(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException {
        return ticketService.getAllTickets(sort, filter, page, pageSize);
    }

    @Override
    public Result getAverageTicketDiscount() throws NotFoundException {
        return ticketService.getAverageTicketDiscount();
    }

    @Override
    public Result getCheaperTicketsByPrice(Integer price) throws NotFoundException {
        return ticketService.getCheaperTicketsByPrice(price);
    }

    @Override
    public TicketResult getMinimalTicketByCreationDate() throws NotFoundException {
        return ticketService.getMinimalTicketByCreationDate();
    }

    @Override
    public TicketResult getTicketById(Long ticketId) throws NotFoundException {
        return ticketService.getTicketById(ticketId);
    }

    @Override
    public TicketResult updateTicket(TicketDTO body) throws NotFoundException {

        return ticketService.updateTicket(body);
    }

    @Override
    public OperationalTicketResult bufferTicket(OperationalTicket body) {
        return ticketService.bufferTicket(body);
    }

    @Override
    public boolean submitTicket(Operation body) {
        return ticketService.submitTicket(body);
    }

    @Override
    public boolean cancelBufferTicket(Operation body) {
        return ticketService.cancelBufferTicket(body);
    }
}
