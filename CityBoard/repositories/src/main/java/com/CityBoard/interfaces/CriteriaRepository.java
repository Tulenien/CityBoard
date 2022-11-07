package com.CityBoard.interfaces;

import com.CityBoard.postgresql.dbmodels.AbstractModel;

import javax.persistence.TypedQuery;
import java.util.function.Predicate;

public interface CriteriaRepository<E extends AbstractModel> {
    TypedQuery<E> createTypedQuery();
    Predicate createPredicates();
    Long countElements(Predicate template);
}
