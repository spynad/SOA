package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Person;
import com.spynad.model.message.PersonResult;
import jakarta.ejb.Remote;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Remote
public interface PersonService {
    PersonResult addPerson(Person body) throws NotFoundException;
    Response deletePerson(Long personId) throws NotFoundException;
    Response getAllPerson(List<String> sort,List<String> filter,Integer page,Integer pageSize) throws NotFoundException;
    Response getPersonById(Long personId) throws NotFoundException;
    Response updatePerson(Person body) throws NotFoundException;
}
