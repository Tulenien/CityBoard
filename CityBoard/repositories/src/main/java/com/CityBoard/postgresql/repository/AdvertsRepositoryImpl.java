package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.AdvertsRepository;
import com.CityBoard.postgresql.PostgresAdvertsMapper;
import com.CityBoard.postgresql.dbmodels.AdvertsPostgres;
import com.CityBoard.postgresql.dbmodels.enums.AdvertStatusPostgres;
import com.CityBoard.repository.AdvertsRepositoryDTO;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdvertsRepositoryImpl implements AdvertsRepository {
    private final EntityManager entityManager;

    private final PostgresAdvertsMapper mapper;

    public AdvertsRepositoryImpl(EntityManager entityManager, PostgresAdvertsMapper advertsMapper) {
        this.entityManager = entityManager;
        this.mapper = advertsMapper;
    }

    public Page<AdvertsRepositoryDTO> findAdvertsPageNoFilter(Pageable pageable) {
        TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel",
                AdvertsPostgres.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a",
                Long.class);
        List<AdvertsRepositoryDTO> resultsList = new ArrayList<>();
        List<AdvertsPostgres> advertsPostgresList = query.getResultList();

        for (AdvertsPostgres advertsPostgres : advertsPostgresList) {
            resultsList.add(mapper.mapToDto(advertsPostgres));
        }
        return new PageImpl<>(resultsList, pageable, countQuery.getSingleResult());
    }

    @Override
    public Page<AdvertsRepositoryDTO> findAdvertsPageWithStatus(Pageable pageable, Integer status) {
        AdvertStatusPostgres statusPostgres = AdvertStatusPostgres.values()[status];
        TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.status=:status", AdvertsPostgres.class);
        query.setParameter("status", status);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
                "status=:status", Long.class);
        countQuery.setParameter("status", status);
        Page<AdvertsPostgres> advertsModelPage = new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
        return mapper.mapAdvertsModelPageToDTO(advertsModelPage);
    }

    //@Override
    //public Page<AdvertDTO> findAdvertsPageWithoutStatus(Pageable pageable, AdvertStatus status) {
    //    TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
    //            "a.status<>:status", AdvertsPostgres.class);
    //    query.setParameter("status", status);
    //    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    //    query.setMaxResults(pageable.getPageSize());
    //    TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
    //            "status<>:status", Long.class);
    //    countQuery.setParameter("status", status);
    //    return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    //}
//
    //@Override
    //public Page<AdvertDTO> findAdvertsPageByNotAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId) {
    //    TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
    //            "a.author_id<>:authorId and a.status=:status", AdvertsPostgres.class);
    //    query.setParameter("status", status);
    //    query.setParameter("authorId", authorId);
    //    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    //    query.setMaxResults(pageable.getPageSize());
    //    TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
    //            "a.author_id<>:authorId and a.status=:status", Long.class);
    //    countQuery.setParameter("status", status);
    //    countQuery.setParameter("authorId", authorId);
    //    return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    //}
//
    //@Override
    //public Page<AdvertDTO> findAdvertsPageByAuthorWithoutStatus(Pageable pageable, AdvertStatus status, Long authorId) {
    //    TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
    //            "a.author_id:authorId and a.status<>:status", AdvertsPostgres.class);
    //    query.setParameter("status", status);
    //    query.setParameter("authorId", authorId);
    //    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    //    query.setMaxResults(pageable.getPageSize());
    //    TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
    //            "a.author_id=:authorId and a.status<>:status", Long.class);
    //    countQuery.setParameter("status", status);
    //    countQuery.setParameter("authorId", authorId);
    //    return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    //}
//
    //@Override
    //public Page<AdvertDTO> findAdvertsPageByAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId) {
    //    TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
    //            "a.author_id=:authorId and a.status=:status", AdvertsPostgres.class);
    //    query.setParameter("status", status);
    //    query.setParameter("authorId", authorId);
    //    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    //    query.setMaxResults(pageable.getPageSize());
    //    TypedQuery<Long> countQuery = entityManager.createQuery("select count(a) from AdvertsModel a where " +
    //            "a.author_id=:authorId and a.status=:status", Long.class);
    //    countQuery.setParameter("status", status);
    //    countQuery.setParameter("authorId", authorId);
    //    return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    //}
//
    //@Override
    //public List<AdvertDTO> findAdvertsListByAuthor(Long authorId) {
    //    TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
    //            "a.author_id=:authorId", AdvertsPostgres.class);
    //    query.setParameter("authorId", authorId);
    //    return query.getResultList();
    //}

    @Override
    public AdvertsRepositoryDTO findAdvertById(Long advertId) {
        TypedQuery<AdvertsPostgres> query = entityManager.createQuery("select a from AdvertsModel a where " +
                "a.id=:id", AdvertsPostgres.class);
        query.setParameter("id", advertId);
        AdvertsPostgres advert = query.getSingleResult();
        if (advert == null) {
            return null;
        }
        return mapper.mapToDto(advert);
    }

    @Override
    public void persist(AdvertsRepositoryDTO dto) {
        entityManager.persist(mapper.mapToModel(dto));
    }

    @Override
    public void update(AdvertsRepositoryDTO dto) {
        Session session = entityManager.unwrap(Session.class);
        session.update(mapper.mapToModel(dto));
    }
}
