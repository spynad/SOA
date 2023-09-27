package com.spynad.firstservice.api;

import com.spynad.firstservice.model.*;
import com.spynad.firstservice.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.math.BigDecimal;

import com.spynad.firstservice.model.Ticket;
import com.spynad.firstservice.model.TicketsArray;

import java.util.Map;
import java.util.List;

import com.spynad.firstservice.exception.NotFoundException;

import java.io.InputStream;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;

import jakarta.validation.constraints.*;

@Path("/ticket")

public class TicketApi {

    @Inject
    TicketService service;

    @POST
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Operation(summary = "Add a new ticket", description = "Add a new ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response addTicket(
            @Parameter(description = "Create a new ticket", required = true) Ticket body
            , @Context SecurityContext securityContext)
            throws NotFoundException {
        return service.addTicket(body, securityContext);
    }

    @DELETE
    @Path("/{ticketId}")
    @Operation(summary = "Delete a ticket", description = "delete a ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response deleteTicket(@PathParam("ticketId") Long ticketId, @Context SecurityContext securityContext)
            throws NotFoundException {
        return service.deleteTicket(ticketId, securityContext);
    }

    @GET
    @Produces({"application/xml"})
    @Operation(summary = "Get list of tickets", description = "Get list of tickets", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = TicketsArray.class))),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getAllTickets(@QueryParam("sort") List<String> sort, @QueryParam("filter") List<String> filter, @Min(0) @QueryParam("page") Integer page, @Min(1) @QueryParam("pageSize") Integer pageSize, @Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getAllTickets(sort, filter, page, pageSize, securityContext);
    }

    @POST
    @Path("/getAverageTicketDiscount")
    @Produces({"application/xml"})
    @Operation(summary = "Get average discount of tickets", description = "Returns an average discount value", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getAverageTicketDiscount(@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getAverageTicketDiscount(securityContext);
    }

    @POST
    @Path("/getCheaperTicketsByPrice/{price}")
    @Produces({"application/xml"})
    @Operation(summary = "Find tickets with the lower price than given", description = "Returns a list of tickets", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid price supplied"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getCheaperTicketsByPrice(@PathParam("price") Integer price, @Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getCheaperTicketsByPrice(price, securityContext);
    }

    @POST
    @Path("/getMinimalTicketByCreationDate")
    @Produces({"application/xml"})
    @Operation(summary = "Find minimal ticket by creation date field", description = "Returns a single ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getMinimalTicketByCreationDate(@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getMinimalTicketByCreationDate(securityContext);
    }

    @GET
    @Path("/{ticketId}")
    @Produces({"application/xml"})
    @Operation(summary = "Find ticket by ID", description = "Returns a single ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getTicketById(@PathParam("ticketId") Long ticketId, @Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getTicketById(ticketId, securityContext);
    }

    @PUT

    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Operation(summary = "Update an existing ticket", description = "Update an existing ticket by Id", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response updateTicket(
            @Parameter(description = "Update an existent ticket", required = true) Ticket body
            , @Context SecurityContext securityContext)
            throws NotFoundException {
        return service.updateTicket(body, securityContext);
    }
}
