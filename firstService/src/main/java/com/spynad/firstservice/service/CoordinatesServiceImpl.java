package com.spynad.firstservice.service;

import com.spynad.firstservice.exception.NotFoundException;
import com.spynad.firstservice.model.Coordinates;
import com.spynad.firstservice.model.message.ApiResponseMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class CoordinatesServiceImpl implements CoordinatesService {
    public Response addCoordinates(Coordinates body,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response deleteCoordinates(Long coordinatesId,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getAllCoordinates(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getCoordinatesById(Long coordinatesId,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response updateCoordinates(Coordinates body,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}

