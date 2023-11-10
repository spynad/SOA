package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Person;
import jakarta.ejb.Remote;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Remote
public interface PersonService {
    Response addPerson(Person body,SecurityContext securityContext) throws NotFoundException;
    Response deletePerson(Long personId,SecurityContext securityContext) throws NotFoundException;
    Response getAllPerson(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getPersonById(Long personId,SecurityContext securityContext) throws NotFoundException;
    Response updatePerson(Person body,SecurityContext securityContext) throws NotFoundException;
}
