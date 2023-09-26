package com.spynad.firstservice.service;

import com.spynad.firstservice.api.*;
import com.spynad.firstservice.model.*;

import com.spynad.firstservice.model.Person;
import com.spynad.firstservice.model.PersonArray;

import java.util.List;
import java.util.Map;
import com.spynad.firstservice.exception.NotFoundException;

import java.io.InputStream;

import com.spynad.firstservice.model.message.ApiResponseMessage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@RequestScoped
public class PersonServiceImpl implements PersonService {
    public Response addPerson(Person body,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response deletePerson(Long personId,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getAllPerson(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getPersonById(Long personId,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response updatePerson(Person body,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
