package com.CityBoard.postgresql.repository;

import com.CityBoard.common.repository.RequestsRepository;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.postgresql.dbmodels.RequestsModelImpl;
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
    public RequestsModelImpl findRequestById(Long id) {
        TypedQuery<RequestsModelImpl> query = entityManager.createQuery("select r from RequestsModel r where r.id=:id",
                RequestsModelImpl.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public List<RequestsModelImpl> findIncomingRequestsListByAuthor(Long authorId) {
        TypedQuery<RequestsModelImpl> query = entityManager.createQuery("select r from RequestsModel r where " +
                "r.advert_id in (select a.id from AdvertsModel where a.author_id =:authorId) " +
                "and r.status=:status", RequestsModelImpl.class);
        query.setParameter("authorId", authorId);
        query.setParameter("status", RequestStatus.PENDING);
        return query.setParameter("authorId", authorId).getResultList();

    }

    @Override
    public List<RequestsModelImpl> findOutgoingRequestsListByAuthor(Long authorId) {
        TypedQuery<RequestsModelImpl> query = entityManager.createQuery("select r from RequestsModel r where " +
                "r.advert_id=:authorId and r.status<>:status", RequestsModelImpl.class);
        query.setParameter("authorId", authorId);
        query.setParameter("status", RequestStatus.DELETED);
        return query.setParameter("authorId", authorId).getResultList();
    }

    @Override
    public void persist(RequestsModelImpl model) {
        entityManager.persist(model);
    }

    @Override
    public void update(RequestsModelImpl model) {
        Session session = entityManager.unwrap(Session.class);
        session.update(model);
    }
}
