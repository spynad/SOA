package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.Filter;
import com.spynad.firstservice.model.Person;
import com.spynad.firstservice.model.PersonArray;
import com.spynad.firstservice.model.Sort;

import java.util.List;

public interface PersonRepository {

    PersonArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size);
    Person getPersonById(Long id);
    Person savePerson(Person person);
    Person updatePerson(Person person);
    void deletePerson(Person person);
}
