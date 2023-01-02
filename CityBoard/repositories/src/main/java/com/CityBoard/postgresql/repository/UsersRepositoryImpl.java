package com.CityBoard.postgresql.repository;

import com.CityBoard.common.repository.UsersRepository;
import com.CityBoard.postgresql.dbmodels.UsersModelImpl;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
    private final EntityManager entityManager;

    public UsersRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<UsersModelImpl> findAllUsers(Pageable pageable) {
        TypedQuery<UsersModelImpl> query = entityManager.createQuery("select u from UsersModel u", UsersModelImpl.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(u) from UsersModel u", Long.class);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public UsersModelImpl findUserById(Long id) {
        TypedQuery<UsersModelImpl> query = entityManager.createQuery("select u from UsersModel u where " +
                "u.id=:id", UsersModelImpl.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public UsersModelImpl findUserByUsername(String username) {
        TypedQuery<UsersModelImpl> query = entityManager.createQuery("select u from UsersModel u where " +
                "u.username=:username", UsersModelImpl.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public void persist(UsersModelImpl model) {
        entityManager.persist(model);
    }

    @Override
    public void update(UsersModelImpl model) {
        Session session = entityManager.unwrap(Session.class);
        session.update(model);
    }
}
