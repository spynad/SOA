package com.spynad.firstservice.api;

import com.spynad.firstservice.model.*;
import com.spynad.firstservice.service.CoordinatesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.spynad.firstservice.model.Coordinates;
import com.spynad.firstservice.model.CoordinatesArray;

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
@Path("/coordinates")
public class CoordinatesApi  {

    @Inject CoordinatesService service;

    @POST

    @Consumes({ "application/xml" })
    @Produces({ "application/xml" })
    @Operation(summary = "Add new coordinates", description = "Add new coordinates object", tags={ "coordinates" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Coordinates.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response addCoordinates(
            @Parameter(description = "Create a new person" ,required=true) Coordinates body
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.addCoordinates(body,securityContext);
    }
    @DELETE
    @Path("/{coordinatesId}")


    @Operation(summary = "Delete a coordinates", description = "Delete a coordinates object", tags={ "coordinates" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Coordinates not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response deleteCoordinates( @PathParam("coordinatesId") Long coordinatesId,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.deleteCoordinates(coordinatesId,securityContext);
    }
    @GET


    @Produces({ "application/xml" })
    @Operation(summary = "Get list of coordinates", description = "Get list of coordinates", tags={ "coordinates" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = CoordinatesArray.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response getAllCoordinates(  @QueryParam("sort") List<String> sort,  @QueryParam("filter") List<String> filter, @Min(0)  @DefaultValue("0") @QueryParam("page") Integer page, @Min(1)  @DefaultValue("10") @QueryParam("pageSize") Integer pageSize,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getAllCoordinates(sort,filter,page,pageSize,securityContext);
    }
    @GET
    @Path("/{coordinatesId}")

    @Produces({ "application/xml" })
    @Operation(summary = "Find coordinates by ID", description = "Returns a single coordinates object", tags={ "coordinates" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Coordinates.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Coordinates not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response getCoordinatesById( @PathParam("coordinatesId") Long coordinatesId,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.getCoordinatesById(coordinatesId,securityContext);
    }
    @PUT

    @Consumes({ "application/xml" })
    @Produces({ "application/xml" })
    @Operation(summary = "Update an existing coordinates", description = "Update an existing coordinates object by Id", tags={ "coordinates" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Coordinates.class))),

            @ApiResponse(responseCode = "400", description = "Validation exception"),

            @ApiResponse(responseCode = "404", description = "Coordinates not found"),

            @ApiResponse(responseCode = "500", description = "Internal error occurred.") })
    public Response updateCoordinates(
            @Parameter(description = "Update an existent coordinates" ,required=true) Coordinates body
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        return service.updateCoordinates(body,securityContext);
    }
}
