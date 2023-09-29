package com.spynad.firstservice.service;

import com.spynad.firstservice.exception.NotFoundException;
import com.spynad.firstservice.model.*;
import com.spynad.firstservice.model.message.ApiResponseMessage;
import com.spynad.firstservice.repository.TicketRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class TicketServiceImpl implements TicketService {

    @Inject
    private TicketRepository repository;

    @Transactional
    public Response addTicket(Ticket body,SecurityContext securityContext)
            throws NotFoundException {
        try {
            if (body == null) return Response.status(400).build();
            if (body.getId() != null) {
                return Response.status(400).entity("").build();
            }
            body.setCreationDate(Date.from(Instant.now()));
            Ticket ticket = repository.saveTicket(body);
            return Response.ok().entity(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Transactional
    public Response deleteTicket(Long ticketId,SecurityContext securityContext)
            throws NotFoundException {
        try {
            Ticket ticket = repository.getTicketById(ticketId);
            if (ticket == null) return Response.status(404).build();
            repository.deleteTicket(ticket);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    public Response getAllTickets(List<String> sortList,List<String> filterList,Integer page,Integer pageSize,SecurityContext securityContext)
            throws NotFoundException {
        if (page != null && pageSize == null) pageSize = 10;
        if (pageSize != null && page == null) page = 1;
        if (page != null && page == 0) page = 1;

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

        TicketsArray entityPage;

        try {
            entityPage = repository.getSortedAndFilteredPage(sorts, filters, page, pageSize);
        } catch (NullPointerException e){
            return Response.status(400).entity(new ApiResponseMessage("query params invalid")).build();
        } catch (NumberFormatException e) {
            return Response.status(400).entity(new ApiResponseMessage("bad values in filters")).build();
        }

        /*Page<Person> ret = new Page<>();
        ret.setObjects(entityPage.getObjects());
        ret.setPage(entityPage.getPage());
        ret.setPageSize(entityPage.getPageSize());
        ret.setTotalPages(entityPage.getTotalPages());
        ret.setTotalCount(entityPage.getTotalCount());*/

        //return ret;
        return Response.ok().entity(entityPage).build();
    }
    public Response getAverageTicketDiscount(SecurityContext securityContext)
            throws NotFoundException {
        List<Ticket> tickets = repository.getAllTickets();
        Double avg = tickets.stream().collect(Collectors.averagingLong(Ticket::getDiscount));
        return Response.ok().entity(avg).build();
    }
    public Response getCheaperTicketsByPrice(Integer price,SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage("magic!")).build();
    }
    public Response getMinimalTicketByCreationDate(SecurityContext securityContext)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage("magic!")).build();
    }
    public Response getTicketById(Long ticketId,SecurityContext securityContext)
            throws NotFoundException {
        try {
            Ticket ticket = repository.getTicketById(ticketId);
            if (ticket == null) return Response.status(404).entity(new ApiResponseMessage("ticket not found")).build();

            return Response.ok().entity(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(e.getClass().getName() + ' ' + e.getMessage())).build();
        }
    }

    @Transactional
    public Response updateTicket(Ticket body,SecurityContext securityContext)
            throws NotFoundException {
        try {
            Ticket ticket = repository.updateTicket(body);
            return Response.ok().entity(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(e.getClass().getName() + ' ' + e.getMessage())).build();
        }
    }
}
