package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.*;
import com.spynad.model.message.PersonListResult;
import com.spynad.model.message.PersonResult;
import com.spynad.model.message.Result;
import com.spynad.repository.PersonRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jboss.ejb3.annotation.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
@Pool("slsb-strict-max-pool")
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Inject
    private PersonRepository repository;

    @Transactional
    public PersonResult addPerson(Person body)
            throws NotFoundException {
        try {
            if (body == null) return new PersonResult("empty person", null, 400);
            if (body.getId() != null) {
                return new PersonResult("id is invalid in this context", null, 404);
            }
            Person person = repository.savePerson(body);
            return new PersonResult("", person, 201);
        } catch (Exception e) {
            e.printStackTrace();
            return new PersonResult("addPerson unknown error", null, 500);
        }
    }

    @Transactional
    public Result deletePerson(Long personId)
            throws NotFoundException {
        try {
            Person person = repository.getPersonById(personId);
            if (person == null)  return new Result("id is invalid in this context", 404);
            repository.deletePerson(person);

            return new Result("", 200);
        } catch (Exception e) {
            return new Result("server error", 500);
        }
    }
    public PersonListResult getAllPerson(List<String> sortList,List<String> filterList,Integer page,Integer pageSize)
            throws NotFoundException {
        if (page != null && pageSize == null) pageSize = 10;
        if (pageSize != null && page == null ) page = 1;
        if (page != null && page == 0) page = 1;

        Pattern nestedFieldNamePattern = Pattern.compile("(.*)\\.(.*)");
        Pattern lhsPattern = Pattern.compile("(.*)\\[(.*)\\]=(.*)");

        List<Sort> sorts = new ArrayList<>();


        if (sortList != null && !sortList.isEmpty()){
            boolean containsOppositeSorts = sortList.stream().allMatch(e1 ->
                    sortList.stream().allMatch(e2 -> Objects.equals(e1, "-" + e2))
            );

            if (containsOppositeSorts){
                throw new IllegalArgumentException("Request contains opposite sort parameters");
            }

            for (String sort: sortList){
                boolean desc = sort.startsWith("-");
                String sortFieldName = desc ? sort.split("-")[1] : sort;
                String nestedName = null;

                Matcher matcher = nestedFieldNamePattern.matcher(sortFieldName);

                if (matcher.find()){
                    String nestedField = matcher.group(2).substring(0, 1).toLowerCase() + matcher.group(2).substring(1);
                    sortFieldName = matcher.group(1);
                    nestedName = nestedField;
                }

                sorts.add(Sort.builder()
                        .desc(desc)
                        .fieldName(sortFieldName)
                        .nestedName(nestedName)
                        .build()
                );
            }
        }

        List<Filter> filters = new ArrayList<>();

        for (String filter : filterList){
            Matcher matcher = lhsPattern.matcher(filter);
            String fieldName = null, fieldValue = null;
            FilteringOperation filteringOperation = null;
            String nestedName = null;

            if (matcher.find()){
                fieldName = matcher.group(1);

                Matcher nestedFieldMatcher = nestedFieldNamePattern.matcher(fieldName);
                if (nestedFieldMatcher.find()){
                    String nestedField = nestedFieldMatcher.group(2).substring(0, 1).toLowerCase() + nestedFieldMatcher.group(2).substring(1);
                    fieldName = nestedFieldMatcher.group(1);
                    nestedName = nestedField;
                }

                filteringOperation = FilteringOperation.fromValue(matcher.group(2));

                if (Objects.equals(fieldName, "eyeColor")){
                    if (!Objects.equals(filteringOperation, FilteringOperation.EQ) && !Objects.equals(filteringOperation, FilteringOperation.NEQ)) {
                        throw new IllegalArgumentException("Only [eq] and [neq] operations are allowed for \"eyeColor\" field");
                    }
                }

                if (Objects.equals(fieldName, "hairColor")){
                    if (!Objects.equals(filteringOperation, FilteringOperation.EQ) && !Objects.equals(filteringOperation, FilteringOperation.NEQ)) {
                        throw new IllegalArgumentException("Only [eq] and [neq] operations are allowed for \"hairColor\" field");
                    }
                }

                if (Objects.equals(fieldName, "country")){
                    if (!Objects.equals(filteringOperation, FilteringOperation.EQ) && !Objects.equals(filteringOperation, FilteringOperation.NEQ)) {
                        throw new IllegalArgumentException("Only [eq] and [neq] operations are allowed for \"country\" field");
                    }
                }

                if (Objects.equals(fieldName, "view")){
                    fieldValue = matcher.group(3).toLowerCase();
                } else fieldValue = matcher.group(3);
            }

            if (fieldName == null || fieldName.isEmpty()){
                throw new IllegalArgumentException("Filter field name is empty");
            }

            if (fieldValue == null || fieldValue.isEmpty()){
                throw new IllegalArgumentException("Filter field value is empty");
            }

            if (Objects.equals(filteringOperation, FilteringOperation.UNDEFINED)){
                throw new IllegalArgumentException("No or unknown filtering operation. Possible values are: eq,neq,gt,lt,gte,lte.");
            }

            filters.add(Filter.builder()
                    .fieldName(fieldName)
                    .nestedName(nestedName)
                    .fieldValue(fieldValue)
                    .filteringOperation(filteringOperation)
                    .build()
            );
        }

        PersonArray entityPage;

        try {
            entityPage = repository.getSortedAndFilteredPage(sorts, filters, page, pageSize);
        } catch (NullPointerException e){
            throw new IllegalArgumentException("Error while getting page. Check query params format. " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            return new PersonListResult("bad values in filters", null, 400);
        }

        return new PersonListResult("", entityPage, 200);
    }
    public PersonResult getPersonById(Long personId)
            throws NotFoundException {
        try {
            Person person = repository.getPersonById(personId);
            if (person == null) return new PersonResult("Invalid ID supplied", null, 404);

            return new PersonResult("", person, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new PersonResult("server error", null, 500);
        }
    }
    @Transactional
    public PersonResult updatePerson(Person body)
            throws NotFoundException {
        try {
            log.info(body.toString());
            Person person = repository.updatePerson(body);
            return new PersonResult("", person, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new PersonResult("server error", null, 500);
        }
    }
}
