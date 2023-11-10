package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.Person;
import com.spynad.model.message.PersonListResult;
import com.spynad.model.message.PersonResult;
import com.spynad.model.message.Result;
import jakarta.ejb.Remote;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Remote
public interface PersonService {
    PersonResult addPerson(Person body) throws NotFoundException;
    Result deletePerson(Long personId) throws NotFoundException;
    PersonListResult getAllPerson(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException;
    PersonResult getPersonById(Long personId) throws NotFoundException;
    PersonResult updatePerson(Person body) throws NotFoundException;
}
