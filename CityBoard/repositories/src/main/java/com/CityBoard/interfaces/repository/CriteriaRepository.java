package com.CityBoard.interfaces;

import com.CityBoard.postgresql.dbmodels.AbstractModel;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

public interface CriteriaRepository<E extends AbstractModel> {
    TypedQuery<E> createTypedQuery();

    Predicate connectPredicates();

    Long countElements(Predicate template);
}
