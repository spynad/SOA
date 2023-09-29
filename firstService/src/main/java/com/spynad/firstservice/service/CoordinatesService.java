package com.spynad.firstservice.service;


import com.spynad.firstservice.exception.NotFoundException;
import com.spynad.firstservice.model.Coordinates;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

public interface CoordinatesService {
    Response addCoordinates(Coordinates body,SecurityContext securityContext) throws NotFoundException;
    Response deleteCoordinates(Long coordinatesId,SecurityContext securityContext) throws NotFoundException;
    Response getAllCoordinates(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getCoordinatesById(Long coordinatesId,SecurityContext securityContext) throws NotFoundException;
    Response updateCoordinates(Coordinates body,SecurityContext securityContext) throws NotFoundException;
}
