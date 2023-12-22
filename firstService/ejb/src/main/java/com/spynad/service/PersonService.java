package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Person;
import com.spynad.model.message.PersonListResult;
import com.spynad.model.message.PersonResult;
import com.spynad.model.message.Result;
import jakarta.ejb.Remote;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Remote
@WebService
public interface PersonService {
    @WebMethod
    PersonResult addPerson(Person body, SecurityContext context) throws NotFoundException;
    @WebMethod
    Result deletePerson(Long personId) throws NotFoundException;
    @WebMethod
    PersonListResult getAllPerson(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException;
    @WebMethod
    PersonResult getPersonById(Long personId) throws NotFoundException;
    @WebMethod
    PersonResult updatePerson(Person body) throws NotFoundException;
}
