package com.CityBoard.postgresql.repository;

import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dbmodels.AdvertsModelImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AdvertsCriteriaRepositoryImpl implements CriteriaRepository<AdvertsModelImpl> {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final List<Predicate> predicates;
    private Pageable pageable;
    private Root<AdvertsModelImpl> root;

    public AdvertsCriteriaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        predicates = new ArrayList<>();
        pageable = null;
        root = null;
    }

    @Override
    public TypedQuery<AdvertsModelImpl> createTypedQuery() {
        CriteriaQuery<AdvertsModelImpl> query = criteriaBuilder.createQuery(AdvertsModelImpl.class);
        root = query.from(AdvertsModelImpl.class);
        if (!predicates.isEmpty()) {
            Predicate predicate = connectPredicates();
            query.where(predicate);
        }
        TypedQuery<AdvertsModelImpl> typedQuery = entityManager.createQuery(query);
        if (pageable != null) {
            typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            typedQuery.setMaxResults(pageable.getPageSize());
        }
        return typedQuery;
    }

    @Override
    public Predicate connectPredicates() {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public Long countElements(Predicate template) {
        if (root != null) {
            CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
            query.select(criteriaBuilder.count(root)).where(template);
            return (Long) entityManager.createQuery(query).getSingleResult();
        }
        return 0L;
    }

    public List<AdvertsModelImpl> getAdvertsListFromQuery(TypedQuery<AdvertsModelImpl> query) {
        return query.getResultList();
    }

    public AdvertsModelImpl getSingleAdvertFromQuery(TypedQuery<AdvertsModelImpl> query) {
        return query.getSingleResult();
    }

    public void setPageable(int pageNumber, int pageSize) {
        pageable = PageRequest.of(pageNumber, pageSize);
    }

    public Pageable getPageable() {
        return pageable;
    }

    // Decorator would be nice
    public void addAdvertWithStatusPredicate(AdvertStatus status) {
        if (root != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }
    }

    public void addAdvertWithoutStatusPredicate(AdvertStatus status) {
        if (root != null) {
            predicates.add(criteriaBuilder.notEqual(root.get("status"), status));
        }
    }

    public void addAdvertWithStatusPredicate(Long authorId) {
        if (root != null) {
            predicates.add(criteriaBuilder.equal(root.get("author_id"), authorId));
        }
    }

    public void addAdvertWithoutStatusPredicate(Long authorId) {
        if (root != null) {
            predicates.add(criteriaBuilder.notEqual(root.get("author_id"), authorId));
        }
    }

    public void addAdvertWithIdPredicate(Long advertId) {
        if (root != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), advertId));
        }
    }
}
