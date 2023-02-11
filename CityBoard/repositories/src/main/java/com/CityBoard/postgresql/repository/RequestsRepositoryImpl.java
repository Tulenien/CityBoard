package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.RequestsRepository;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.postgresql.dbmodels.RequestsPostgres;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RequestsRepositoryImpl implements RequestsRepository {
    private final EntityManager entityManager;

    public RequestsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RequestsPostgres findRequestById(Long id) {
        TypedQuery<RequestsPostgres> query = entityManager.createQuery("select r from RequestsModel r where r.id=:id",
                RequestsPostgres.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public List<RequestsPostgres> findIncomingRequestsListByAuthor(Long authorId) {
        TypedQuery<RequestsPostgres> query = entityManager.createQuery("select r from RequestsModel r where " +
                "r.advert_id in (select a.id from AdvertsModel where a.author_id =:authorId) " +
                "and r.status=:status", RequestsPostgres.class);
        query.setParameter("authorId", authorId);
        query.setParameter("status", RequestStatus.PENDING);
        return query.setParameter("authorId", authorId).getResultList();

    }

    @Override
    public List<RequestsPostgres> findOutgoingRequestsListByAuthor(Long authorId) {
        TypedQuery<RequestsPostgres> query = entityManager.createQuery("select r from RequestsModel r where " +
                "r.advert_id=:authorId and r.status<>:status", RequestsPostgres.class);
        query.setParameter("authorId", authorId);
        query.setParameter("status", RequestStatus.DELETED);
        return query.setParameter("authorId", authorId).getResultList();
    }

    @Override
    public void persist(RequestsPostgres model) {
        entityManager.persist(model);
    }

    @Override
    public void update(RequestsPostgres model) {
        Session session = entityManager.unwrap(Session.class);
        session.update(model);
    }
}
