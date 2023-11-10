package com.spynad.api;

import com.spynad.config.JNDIConfig;
import com.spynad.exception.NotFoundException;
import com.spynad.model.OperationalTicket;
import com.spynad.model.Ticket;
import com.spynad.model.TicketsArray;
import com.spynad.model.message.ApiResponseMessage;
import com.spynad.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.math.BigDecimal;
import java.util.List;

@Path("/ticket")

public class TicketApi {

    TicketService service = JNDIConfig.ticketService();

    @POST
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Operation(summary = "Add a new ticket", description = "Add a new ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response addTicket(
            @Parameter(description = "Create a new ticket", required = true) Ticket body)
            throws NotFoundException {
        return service.addTicket(body);
    }

    @DELETE
    @Path("/{ticketId}")
    @Operation(summary = "Delete a ticket", description = "delete a ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response deleteTicket(@PathParam("ticketId") Long ticketId)
            throws NotFoundException {
        return service.deleteTicket(ticketId);
    }

    @GET
    @Produces({"application/xml"})
    @Operation(summary = "Get list of tickets", description = "Get list of tickets", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = TicketsArray.class))),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getAllTickets(@QueryParam("sort") List<String> sort, @QueryParam("filter") List<String> filter, @Min(0) @QueryParam("page") Integer page, @Min(1) @QueryParam("pageSize") Integer pageSize)
            throws NotFoundException {
        try {
            return service.getAllTickets(sort, filter, page, pageSize);
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(new ApiResponseMessage("bad values in filters")).build();
        }
    }

    @POST
    @Path("/get-average-ticket-discount")
    @Produces({"application/xml"})
    @Operation(summary = "Get average discount of tickets", description = "Returns an average discount value", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getAverageTicketDiscount()
            throws NotFoundException {
        return service.getAverageTicketDiscount();
    }

    @POST
    @Path("/get-cheaper-tickets-by-price/{price}")
    @Produces({"application/xml"})
    @Operation(summary = "Find tickets with the lower price than given", description = "Returns a list of tickets", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid price supplied"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getCheaperTicketsByPrice(@PathParam("price") Integer price)
            throws NotFoundException {
        return service.getCheaperTicketsByPrice(price);
    }

    @POST
    @Path("/get-minimal-ticket-by-creation-date")
    @Produces({"application/xml"})
    @Operation(summary = "Find minimal ticket by creation date field", description = "Returns a single ticket", tags = {"ticket"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred.")})
    public Response getMinimalTicketByCreationDate()
            throws NotFoundException {
        return service.getMinimalTicketByCreationDate();
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
    public Response getTicketById(@PathParam("ticketId") Long ticketId)
            throws NotFoundException {
        return service.getTicketById(ticketId);
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
            @Parameter(description = "Update an existent ticket", required = true) Ticket body)
            throws NotFoundException {
        return service.updateTicket(body);
    }

    @POST
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Operation(summary = "Buffer operational ticket", description = "Buffer operational ticket", tags = {"ticket"})
    @Path("/buffer")
    public Response bufferTicket(@Parameter(description = "Buffer operational ticket", required = true) OperationalTicket body) {
        return service.bufferTicket(body);
    }

    @POST
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Operation(summary = "Submit operation", description = "Submit operation", tags = {"ticket"})
    @Path("/buffer/submit")
    public Response submitTicket(@Parameter(description = "Operation", required = true) com.spynad.model.Operation body) {
        return service.submitTicket(body);
    }

    @POST
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Operation(summary = "Cancel operation", description = "Cancel operation", tags = {"ticket"})
    @Path("/buffer/cancel")
    public Response cancelBufferTicket(@Parameter(description = "Cancel", required = true) com.spynad.model.Operation body) {
        return service.cancelBufferTicket(body);
    }
}
