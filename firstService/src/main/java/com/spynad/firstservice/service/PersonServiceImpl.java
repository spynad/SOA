package com.spynad.firstservice.service;

import com.spynad.firstservice.exception.NotFoundException;
import com.spynad.firstservice.model.*;
import com.spynad.firstservice.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    private PersonRepository repository;

    @Transactional
    public Response addPerson(Person body,SecurityContext securityContext)
            throws NotFoundException {
        try {
            if (body == null) return Response.status(400).build();
            if (body.getId() != null) {
                return Response.status(400).entity("id is invalid in this context").build();
            }
            Person person = repository.savePerson(body);
            return Response.ok().entity(person).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Transactional
    public Response deletePerson(Long personId,SecurityContext securityContext)
            throws NotFoundException {
        try {
            Person person = repository.getPersonById(personId);
            if (person == null) return Response.status(404).build();
            repository.deletePerson(person);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    public Response getAllPerson(List<String> sortList,List<String> filterList,Integer page,Integer pageSize,SecurityContext securityContext)
            throws NotFoundException {
        if (page != null && pageSize == null) pageSize = 10;
        if (pageSize != null && page == null) page = 1;
        if (page == 0) page = 1;
        /*if (page != null || pageSize != null){
            if (page == null){
                page = 1;
            }
            if (pageSize == null){
                pageSize = 20;
            }
        }*/

        Pattern nestedFieldNamePattern = Pattern.compile("(.*)\\.(.*)");
        Pattern lhsPattern = Pattern.compile("(.*)\\[(.*)\\]=(.*)");

        List<Sort> sorts = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(sortList)){
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

            if (StringUtils.isEmpty(fieldName)){
                throw new IllegalArgumentException("Filter field name is empty");
            }

            if (StringUtils.isEmpty(fieldValue)){
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
        }

        return Response.ok().entity(entityPage).build();
    }
    public Response getPersonById(Long personId,SecurityContext securityContext)
            throws NotFoundException {
        try {
            Person person = repository.getPersonById(personId);
            if (person == null) return Response.status(404).build();

            return Response.ok().entity(person).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    @Transactional
    public Response updatePerson(Person body,SecurityContext securityContext)
            throws NotFoundException {
        try {
            Person person = repository.updatePerson(body);
            return Response.ok().entity(person).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
