package com.spynad.wsservice;

import com.spynad.config.JNDIConfig;
import com.spynad.exception.NotFoundException;
import com.spynad.model.Person;
import com.spynad.model.message.PersonListResult;
import com.spynad.model.message.PersonResult;
import com.spynad.model.message.Result;
import com.spynad.service.PersonService;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@WebService(endpointInterface = "com.spynad.wsservice.PersonSOAPService")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public class PersonSOAPServiceImpl implements PersonSOAPService {

    PersonService service = JNDIConfig.personService();
    @Override
    public PersonResult addPerson(Person body) throws NotFoundException {
        return service.addPerson(body);
    }

    @Override
    public Result deletePerson(Long personId) throws NotFoundException {
        return service.deletePerson(personId);
    }

    @Override
    public PersonListResult getAllPerson(List<String> sort, List<String> filter, Integer page, Integer pageSize) throws NotFoundException {
        return service.getAllPerson(sort, filter, page, pageSize);
    }

    @Override
    public PersonResult getPersonById(Long personId) throws NotFoundException {
        return service.getPersonById(personId);
    }

    @Override
    public PersonResult updatePerson(Person body) throws NotFoundException {
        return service.updatePerson(body);
    }
}
