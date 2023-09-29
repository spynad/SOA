package com.spynad.firstservice.service;

import com.spynad.firstservice.exception.NotFoundException;
import com.spynad.firstservice.model.Person;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

public interface PersonService {
    Response addPerson(Person body,SecurityContext securityContext) throws NotFoundException;
    Response deletePerson(Long personId,SecurityContext securityContext) throws NotFoundException;
    Response getAllPerson(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getPersonById(Long personId,SecurityContext securityContext) throws NotFoundException;
    Response updatePerson(Person body,SecurityContext securityContext) throws NotFoundException;
}
