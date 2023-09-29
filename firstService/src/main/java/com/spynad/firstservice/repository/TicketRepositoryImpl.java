package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TicketRepositoryImpl implements TicketRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public TicketsArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> flatQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> root = flatQuery.from(Ticket.class);

        CriteriaQuery<Ticket> select = flatQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filtersList)){
            predicates = new ArrayList<>();

            for (Filter filter : filtersList){

                switch (filter.getFilteringOperation()){
                    case EQ:
                        if (filter.getNestedName() != null) {
                            predicates.add(criteriaBuilder.equal(
                                            root.get(filter.getFieldName()).get(filter.getNestedName()),
                                            getTypedFieldValue(filter.getFieldName(), filter.getFieldValue())
                                    )
                            );
                        } else {
                            predicates.add(criteriaBuilder.equal(
                                            root.get(filter.getFieldName()),
                                            getTypedFieldValue(filter.getFieldName(), filter.getFieldValue())
                                    )
                            );
                        }
                        break;
                    case NEQ:
                        if (filter.getNestedName() != null) {
                            predicates.add(criteriaBuilder.notEqual(
                                            root.get(filter.getFieldName()).get(filter.getNestedName()),
                                            getTypedFieldValue(filter.getFieldName(), filter.getFieldValue())
                                    )
                            );
                        } else {
                            predicates.add(criteriaBuilder.notEqual(
                                            root.get(filter.getFieldName()),
                                            getTypedFieldValue(filter.getFieldName(), filter.getFieldValue())
                                    )
                            );
                        }
                        break;
                    case GT:
                        if (filter.getNestedName() != null) {
                            predicates.add(criteriaBuilder.greaterThan(
                                            root.get(filter.getFieldName()).get(filter.getNestedName()),
                                            filter.getFieldValue()
                                    )
                            );
                        } else {
                            predicates.add(criteriaBuilder.greaterThan(
                                            root.get(filter.getFieldName()),
                                            filter.getFieldValue()
                                    )
                            );
                        }
                        break;
                    case LT:
                        if (filter.getNestedName() != null) {
                            predicates.add(criteriaBuilder.lessThan(
                                            root.get(filter.getFieldName()).get(filter.getNestedName()),
                                            filter.getFieldValue()
                                    )
                            );
                        } else {
                            predicates.add(criteriaBuilder.lessThan(
                                            root.get(filter.getFieldName()),
                                            filter.getFieldValue()
                                    )
                            );
                        }
                        break;
                    case GTE:
                        if (filter.getNestedName() != null) {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                                            root.get(filter.getFieldName()).get(filter.getNestedName()),
                                            filter.getFieldValue()
                                    )
                            );
                        } else {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                                            root.get(filter.getFieldName()),
                                            filter.getFieldValue()
                                    )
                            );
                        }
                        break;
                    case LTE:
                        if (filter.getNestedName() != null){
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                                            root.get(filter.getFieldName()).get(filter.getNestedName()),
                                            filter.getFieldValue()
                                    )
                            );
                        } else {
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                                            root.get(filter.getFieldName()),
                                            filter.getFieldValue()
                                    )
                            );
                        }
                        break;
                    case UNDEFINED:
                        break;
                }
            }

            select.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        if (CollectionUtils.isNotEmpty(sortList)){
            List<Order> orderList = new ArrayList<>();

            for (Sort sortItem : sortList){
                if (sortItem.isDesc()){
                    if (sortItem.getNestedName() != null){
                        orderList.add(criteriaBuilder.desc(root.get(sortItem.getFieldName()).get(sortItem.getNestedName())));
                    } else {
                        orderList.add(criteriaBuilder.desc(root.get(sortItem.getFieldName())));
                    }
                } else {
                    if (sortItem.getNestedName() != null){
                        orderList.add(criteriaBuilder.asc(root.get(sortItem.getFieldName()).get(sortItem.getNestedName())));
                    } else {
                        orderList.add(criteriaBuilder.asc(root.get(sortItem.getFieldName())));
                    }
                }
            }
            select.orderBy(orderList);
        }

        TypedQuery<Ticket> typedQuery = entityManager.createQuery(select);

        TicketsArray ret = new TicketsArray();

        if (page != null && size != null){
            typedQuery.setFirstResult((page - 1) * size);
            typedQuery.setMaxResults(size);

            long countResult = 0;

            if (CollectionUtils.isNotEmpty(predicates)){
                Query queryTotal = entityManager.createQuery("SELECT COUNT(f.id) FROM Ticket f");
                countResult = (long) queryTotal.getSingleResult();
            } else {
                CriteriaBuilder qb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = qb.createQuery(Long.class);
                cq.select(qb.count(cq.from(Person.class)));
                cq.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
                countResult = entityManager.createQuery(cq).getSingleResult();
            }

            ret.setPage(Long.valueOf(page));
            ret.setPageSize(Long.valueOf(size));
            ret.setPagesTotal((long) Math.ceil((countResult * 1.0) / size));
            //ret.setTotalCount(countResult);
        }

        ret.setTickets(typedQuery.getResultList());
        //ret.setObjects(typedQuery.getResultList());

        return ret;
    }

    @Override
    public Ticket getTicketById(Long id) {
        return entityManager.find(Ticket.class, id);
    }

    @Override
    public List<Ticket> getAllTickets() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
        Root<Ticket> rootEntry = cq.from(Ticket.class);
        CriteriaQuery<Ticket> all = cq.select(rootEntry);

        TypedQuery<Ticket> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        entityManager.persist(ticket);
        return ticket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return entityManager.merge(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        entityManager.remove(ticket);
    }

    private Object getTypedFieldValue(String fieldName, String fieldValue) {
        switch (fieldName) {
            case "id", "discount", "person.id":
                return Long.valueOf(fieldValue);
            case "price":
                return Integer.valueOf(fieldValue);
            case "creationDate":
                return Date.valueOf(fieldValue);
            case "refundable":
                return Boolean.valueOf(fieldValue);
            case "x", "y", "person.weight":
                return Float.valueOf(fieldValue);
            case "person.eyeColor":
                return Person.EyeColorEnum.fromValue(fieldValue);
            case "person.hairColor":
                return Person.HairColorEnum.fromValue(fieldValue);
            case "person.country":
                return Person.CountryEnum.fromValue(fieldValue);
            default:
                return null;
        }
    }
}
