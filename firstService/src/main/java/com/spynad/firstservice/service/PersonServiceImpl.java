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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@RequestScoped
public class PersonServiceImpl implements PersonService {

    @PersistenceContext()
    private EntityManager entityManager;

    @Transactional
    public Response addPerson(Person body,SecurityContext securityContext)
            throws NotFoundException {
        entityManager.persist(body);
        // do some magic!
        return Response.ok().entity(body).build();
        //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Transactional
    public Response deletePerson(Long personId,SecurityContext securityContext)
            throws NotFoundException {
        Person person = entityManager.find(Person.class, personId);
        entityManager.remove(person);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "deleted successfully")).build();
    }
    public Response getAllPerson(List<String> sort,List<String> filter,Integer page,Integer pageSize,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    public Response getPersonById(Long personId,SecurityContext securityContext)
            throws NotFoundException {
        Person person = entityManager.find(Person.class, personId);
        // do some magic!
        return Response.ok().entity(person).build();
    }
    @Transactional
    public Response updatePerson(Person body,SecurityContext securityContext)
            throws NotFoundException {
        Person person = entityManager.find(Person.class, body.getId());
        entityManager.merge(body);
        // do some magic!
        return Response.ok().entity(body).build();
    }
}
