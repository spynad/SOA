package com.spynad.firstservice.service;


import com.spynad.firstservice.api.*;
import com.spynad.firstservice.model.*;

import com.spynad.firstservice.model.Coordinates;
import com.spynad.firstservice.model.CoordinatesArray;

import java.util.List;
import java.util.Map;
import com.spynad.firstservice.exception.NotFoundException;

import java.io.InputStream;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public interface CoordinatesService {
    Response addCoordinates(Coordinates body,SecurityContext securityContext) throws NotFoundException;
    Response deleteCoordinates(Long coordinatesId,SecurityContext securityContext) throws NotFoundException;
    Response getAllCoordinates(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getCoordinatesById(Long coordinatesId,SecurityContext securityContext) throws NotFoundException;
    Response updateCoordinates(Coordinates body,SecurityContext securityContext) throws NotFoundException;
}
