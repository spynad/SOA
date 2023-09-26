package com.spynad.firstservice.service;

import com.spynad.firstservice.api.*;
import com.spynad.firstservice.model.*;

import java.math.BigDecimal;
import com.spynad.firstservice.model.Ticket;
import com.spynad.firstservice.model.TicketsArray;

import java.util.List;
import java.util.Map;
import com.spynad.firstservice.exception.NotFoundException;

import java.io.InputStream;

import com.spynad.firstservice.model.message.ApiResponseMessage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@RequestScoped
public class TicketServiceImpl implements TicketService {
    public Response addTicket(Ticket body,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response deleteTicket(Long ticketId,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getAllTickets(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getAverageTicketDiscount(SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getCheaperTicketsByPrice(Integer price,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getMinimalTicketByCreationDate(SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getTicketById(Long ticketId,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response updateTicket(Ticket body,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
