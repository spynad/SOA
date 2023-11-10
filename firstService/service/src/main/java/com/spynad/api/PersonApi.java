package com.spynad.api;

import com.spynad.config.JNDIConfig;
import com.spynad.exception.NotFoundException;
import com.spynad.model.Person;
import com.spynad.model.PersonArray;
import com.spynad.model.message.ApiResponseMessage;

import com.spynad.model.message.PersonResult;
import com.spynad.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
@Path("/person")
public class PersonApi  {

    PersonService service = JNDIConfig.personService();

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    @Operation(summary = "Add a new person", description = "Add a new person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Person.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response addPerson(
            @Parameter(description = "Create a new person" ,required=true) Person body)
            throws NotFoundException {
        PersonResult result = service.addPerson(body);
        if (result.getResult() == null) {
            return Response.status(400).entity(result.getMessage()).build();
        }
        return Response.ok().entity(result.getResult()).build();
    }
    @DELETE
    @Path("/{personId}")
    @Operation(summary = "Delete a person", description = "delete a person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Person not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response deletePerson( @PathParam("personId") Long personId)
            throws NotFoundException {
        return service.deletePerson(personId);
    }
    @GET
    @Produces({ "application/xml" })
    @Operation(summary = "Get list of person", description = "Get list of person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = PersonArray.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response getAllPerson(  @QueryParam("sort") List<String> sort,  @QueryParam("filter") List<String> filter, @Min(0) @QueryParam("page") Integer page, @Min(1) @QueryParam("pageSize") Integer pageSize)
            throws NotFoundException {
        try {
            return service.getAllPerson(sort, filter, page, pageSize);
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(new ApiResponseMessage("bad values in filters")).build();
        }
    }
    @GET
    @Path("/{personId}")
    @Produces({ "application/xml" })
    @Operation(summary = "Find person by ID", description = "Returns a single person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Person.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Person not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response getPersonById( @PathParam("personId") Long personId)
            throws NotFoundException {
        return service.getPersonById(personId);
    }
    @PUT
    @Consumes({ "application/xml" })
    @Produces({ "application/xml" })
    @Operation(summary = "Update an existing person", description = "Update an existing person by Id", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Person.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "404", description = "Person not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response updatePerson(
            @Parameter(description = "Update an existent person" ,required=true) Person body)
            throws NotFoundException {
        return service.updatePerson(body);
    }
}

