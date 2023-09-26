package com.spynad.firstservice.service;

import com.spynad.firstservice.api.*;
import com.spynad.firstservice.model.*;

import com.spynad.firstservice.model.Person;
import com.spynad.firstservice.model.PersonArray;

import java.util.List;
import java.util.Map;
import com.spynad.firstservice.exception.NotFoundException;

import java.io.InputStream;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public interface PersonService {
    Response addPerson(Person body,SecurityContext securityContext) throws NotFoundException;
    Response deletePerson(Long personId,SecurityContext securityContext) throws NotFoundException;
    Response getAllPerson(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext) throws NotFoundException;
    Response getPersonById(Long personId,SecurityContext securityContext) throws NotFoundException;
    Response updatePerson(Person body,SecurityContext securityContext) throws NotFoundException;
}
