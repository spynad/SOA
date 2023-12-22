package com.spynad.service;

import com.spynad.exception.NotFoundException;
import com.spynad.model.*;
import com.spynad.model.message.*;
import com.spynad.repository.OperationalTicketRepository;
import com.spynad.repository.PersonRepository;
import com.spynad.repository.TicketRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.ejb3.annotation.Pool;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
@Pool("slsb-strict-max-pool")
public class TicketServiceImpl implements TicketService {

    @Inject
    private TicketRepository repository;

    @Inject
    private OperationalTicketRepository opRepository;

    @Inject
    private PersonRepository personRepository;

    @Transactional
    public TicketResult addTicket(Ticket body)
            throws NotFoundException {
        try {
            if (body == null) return new TicketResult("empty ticket", null, 400);
            if (body.getId() != null) {
                return new TicketResult("id is invalid in this context", null, 404);
            }
            
            body.setCreationDate(Date.from(Instant.now()));
            Ticket ticket = repository.saveTicket(body);
            return new TicketResult("", ticket, 201);
        } catch (Exception e) {
            e.printStackTrace();
            return new TicketResult("addTicket unknown error", null, 500);
        }
    }

    @Transactional
    public Result deleteTicket(Long ticketId)
            throws NotFoundException {
        try {
            Ticket ticket = repository.getTicketById(ticketId);
            if (ticket == null) return new Result("unknown ticket", 404);
            repository.deleteTicket(ticket);

            return new Result("", 200);
        } catch (Exception e) {
            return new Result("", 500);
        }
    }
    public TicketListResult getAllTickets(List<String> sortList,List<String> filterList,Integer page,Integer pageSize)
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

        TicketsArray entityPage;

        try {
            entityPage = repository.getSortedAndFilteredPage(sorts, filters, page, pageSize);
        } catch (NullPointerException e) {
            return new TicketListResult("query params invalid", null, 400);
        } catch (NumberFormatException e) {
            return new TicketListResult("bad values in filters", null, 400);
        }

        return new TicketListResult("", entityPage, 200);
    }
    public Result getAverageTicketDiscount()
            throws NotFoundException {
        List<Ticket> tickets = repository.getAllTickets();
        Double avg = tickets.stream().collect(Collectors.averagingLong(Ticket::getDiscount));
        return new Result(avg.toString(), 200);
    }
    public Result getCheaperTicketsByPrice(Integer price)
            throws NotFoundException {
        List<Ticket> tickets = repository.getAllTickets();
        Long count = tickets.stream().map(Ticket::getPrice).filter(t -> t < price).count();
        return new Result(count.toString(), 200);
    }
    public TicketResult getMinimalTicketByCreationDate()
            throws NotFoundException {
        List<Ticket> tickets = repository.getAllTickets();
        Ticket ticket = tickets.stream().min(Comparator.comparing(Ticket::getCreationDate)).get();
        return new TicketResult("", ticket, 200);
    }
    public TicketResult getTicketById(Long ticketId)
            throws NotFoundException {
        try {
            Ticket ticket = repository.getTicketById(ticketId);
            if (ticket == null) return new TicketResult("ticket not found", null, 404);

            return new TicketResult("", ticket, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new TicketResult(e.getMessage(), null, 500);
        }
    }

    @Transactional
    public TicketResult updateTicket(Ticket body)
            throws NotFoundException {
        try {
            Ticket ticket = repository.updateTicket(body);
            return new TicketResult("", ticket, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new TicketResult(e.getMessage(), null, 500);
        }
    }

    @Override
    @Transactional
    public OperationalTicketResult bufferTicket(OperationalTicket body) {
        try {
            if (body == null || body.getId() != null) {
                return new OperationalTicketResult("unknown operational ticket", null, 400);
            }
            body.setCreationDate(Date.from(Instant.now()));
            body.setStatus(OperationalTicket.StatusEnum.PENDING);
            OperationalTicket ticket = opRepository.saveOperationalTicket(body);
            return new OperationalTicketResult("", ticket, 200);
        } catch (Exception e) {
            return new OperationalTicketResult(e.getMessage(), null, 500);
        }
    }

    @Override
    @Transactional
    public boolean submitTicket(Operation body) {
        try {
            List<OperationalTicket> tickets = opRepository.getOperationalTicketsByOperationId(body.getOperationId());
            for (OperationalTicket t : tickets) {
                if (t.getStatus().equals(OperationalTicket.StatusEnum.PENDING)) {
                    if (t.getTicketId() == null) {
                        Ticket ticket = new Ticket();

                        updateTicketFields(t, ticket);

                        repository.saveTicket(ticket);
                    } else {
                        Ticket ticket = repository.getTicketById(t.getTicketId());
                        updateTicketFields(t, ticket);

                        repository.updateTicket(ticket);
                    }
                    t.setStatus(OperationalTicket.StatusEnum.SAVED);
                    opRepository.updateOperationalTicket(t);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void updateTicketFields(OperationalTicket t, Ticket ticket) {
        if (t.getPersonId() != null) {
            Person person = personRepository.getPersonById(t.getPersonId());
            ticket.setPersonId(person);
        } else ticket.setPersonId(null);
        ticket.setDiscount(t.getDiscount());
        ticket.setName(t.getName());
        ticket.setPrice(t.getPrice());
        ticket.setType(t.getType());
        ticket.setRefundable(t.getRefundable());
        ticket.setCreationDate(Date.from(Instant.now()));
        ticket.setCoordinates(new Coordinates(t.getCoordinates().getX(), t.getCoordinates().getY()));
    }

    @Override
    @Transactional
    public boolean cancelBufferTicket(Operation body) {
        try {
            List<OperationalTicket> tickets = opRepository.getPendingOperationalTickets(body.getOperationId());
            for (OperationalTicket t : tickets) {
                t.setStatus(OperationalTicket.StatusEnum.CANCELED);
                opRepository.updateOperationalTicket(t);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
