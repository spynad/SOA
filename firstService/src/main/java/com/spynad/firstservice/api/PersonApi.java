package com.spynad.firstservice.api;

import com.spynad.firstservice.model.*;
import com.spynad.firstservice.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.spynad.firstservice.model.Person;
import com.spynad.firstservice.model.PersonArray;

import java.util.Map;
import java.util.List;
import com.spynad.firstservice.exception.NotFoundException;

import java.io.InputStream;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;

import jakarta.validation.constraints.*;
@Path("/person")
public class PersonApi  {

    @Inject PersonService service;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    @Operation(summary = "Add a new person", description = "Add a new person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Person.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response addPerson(
            @Parameter(description = "Create a new person" ,required=true) Person body
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.addPerson(body,securityContext);
    }
    @DELETE
    @Path("/{personId}")
    @Operation(summary = "Delete a person", description = "delete a person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Person not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response deletePerson( @PathParam("personId") Long personId,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.deletePerson(personId,securityContext);
    }
    @GET
    @Produces({ "application/xml" })
    @Operation(summary = "Get list of person", description = "Get list of person", tags={ "person" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = PersonArray.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response getAllPerson(  @QueryParam("sort") List<String> sort,  @QueryParam("filter") List<String> filter, @Min(0)  @DefaultValue("0") @QueryParam("page") Integer page, @Min(1)  @DefaultValue("10") @QueryParam("pageSize") Integer pageSize,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getAllPerson(sort,filter,page,pageSize,securityContext);
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
    public Response getPersonById( @PathParam("personId") Long personId,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getPersonById(personId,securityContext);
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
            @Parameter(description = "Update an existent person" ,required=true) Person body
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.updatePerson(body,securityContext);
    }
}

