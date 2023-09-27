package com.spynad.firstservice.repository;

import com.spynad.firstservice.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public PersonArray getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filtersList, Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> flatQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = flatQuery.from(Person.class);

        CriteriaQuery<Person> select = flatQuery.select(root);

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

        TypedQuery<Person> typedQuery = entityManager.createQuery(select);

        PersonArray ret = new PersonArray();
        //Page<Person> ret = new Page<>();

        if (page != null && size != null){
            typedQuery.setFirstResult((page - 1) * size);
            typedQuery.setMaxResults(size);

            long countResult = 0;

            if (CollectionUtils.isNotEmpty(predicates)){
                Query queryTotal = entityManager.createQuery("SELECT COUNT(f.id) FROM Person f");
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

        ret.setPersons(typedQuery.getResultList());
        //ret.setObjects(typedQuery.getResultList());

        return ret;
    }

    @Override
    public Person getPersonById(Long id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public Person savePerson(Person person) {
        entityManager.persist(person);
        return person;
    }

    @Override
    public Person updatePerson(Person person) {
        return entityManager.merge(person);
    }

    @Override
    public void deletePerson(Person person) {
        entityManager.remove(person);
    }

    private Object getTypedFieldValue(String fieldName, String fieldValue) {
        if (Objects.equals(fieldName, "eyeColor")) {
            return Boolean.valueOf(fieldValue);
        } else if (Objects.equals(fieldName, "hairColor")) {
            return Person.HairColorEnum.fromValue(fieldValue);
        } else if (Objects.equals(fieldName, "country")) {
            return Person.CountryEnum.fromValue(fieldValue);
        } else return fieldName;
    }
}
