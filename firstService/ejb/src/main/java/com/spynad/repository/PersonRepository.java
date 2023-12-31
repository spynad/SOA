package com.spynad.repository;

import com.spynad.model.Filter;
import com.spynad.model.Person;
import com.spynad.model.PersonArray;
import com.spynad.model.Sort;

import java.util.List;

public interface PersonRepository {

    PersonArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size);
    Person getPersonById(Long id);
    Person savePerson(Person person);
    Person updatePerson(Person person);
    void deletePerson(Person person);
}
