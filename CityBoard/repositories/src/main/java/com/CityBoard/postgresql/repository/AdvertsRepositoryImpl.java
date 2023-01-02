package com.CityBoard.postgresql.repository;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.interfaces.dbmodels.AdvertsModel;
import com.CityBoard.interfaces.mapping.AdvertsModelDTOMapper;
import com.CityBoard.common.repository.AdvertsRepository;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dbmodels.AdvertsModelImpl;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AdvertsRepositoryImpl implements AdvertsRepository {
    private final EntityManager entityManager;
    private final AdvertsModelDTOMapper mapper;

    public AdvertsRepositoryImpl(EntityManager entityManager, AdvertsModelDTOMapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    public Page<AdvertDTO> findAdvertsPageNoFilter(Pageable pageable) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel",
                AdvertsModelImpl.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a",
                Long.class);
        Page<AdvertsModelImpl> advertsModelPage = new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
        return mapper.mapAdvertsModelPageToDTO(advertsModelPage);
    }

    @Override
    public Page<AdvertDTO> findAdvertsPageWithStatus(Pageable pageable, AdvertStatus status) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.status=:status", AdvertsModelImpl.class);
        query.setParameter("status", status);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
                "status=:status", Long.class);
        countQuery.setParameter("status", status);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public Page<AdvertDTO> findAdvertsPageWithoutStatus(Pageable pageable, AdvertStatus status) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.status<>:status", AdvertsModelImpl.class);
        query.setParameter("status", status);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
                "status<>:status", Long.class);
        countQuery.setParameter("status", status);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public Page<AdvertDTO> findAdvertsPageByNotAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.author_id<>:authorId and a.status=:status", AdvertsModelImpl.class);
        query.setParameter("status", status);
        query.setParameter("authorId", authorId);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
                "a.author_id<>:authorId and a.status=:status", Long.class);
        countQuery.setParameter("status", status);
        countQuery.setParameter("authorId", authorId);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public Page<AdvertDTO> findAdvertsPageByAuthorWithoutStatus(Pageable pageable, AdvertStatus status, Long authorId) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.author_id:authorId and a.status<>:status", AdvertsModelImpl.class);
        query.setParameter("status", status);
        query.setParameter("authorId", authorId);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
                "a.author_id=:authorId and a.status<>:status", Long.class);
        countQuery.setParameter("status", status);
        countQuery.setParameter("authorId", authorId);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public Page<AdvertDTO> findAdvertsPageByAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.author_id=:authorId and a.status=:status", AdvertsModelImpl.class);
        query.setParameter("status", status);
        query.setParameter("authorId", authorId);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
                "a.author_id=:authorId and a.status=:status", Long.class);
        countQuery.setParameter("status", status);
        countQuery.setParameter("authorId", authorId);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public List<AdvertDTO> findAdvertsListByAuthor(Long authorId) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.author_id=:authorId", AdvertsModelImpl.class);
        query.setParameter("authorId", authorId);
        return query.getResultList();
    }

    @Override
    public AdvertDTO findAdvertById(Long advertId) {
        TypedQuery<AdvertsModelImpl> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.id=:id", AdvertsModelImpl.class);
        query.setParameter("id", advertId);
        return query.getSingleResult();
    }

    @Override
    public void persist(AdvertDTO model) {
        entityManager.persist(model);
    }

    @Override
    public void update(AdvertDTO model) {
        Session session = entityManager.unwrap(Session.class);
        session.update(model);
    }
}
