package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.UsersRepository;
import com.CityBoard.postgresql.dbmodels.UsersPostgres;
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
    public Page<UsersPostgres> findAllUsers(Pageable pageable) {
        TypedQuery<UsersPostgres> query = entityManager.createQuery("select u from UsersModel u", UsersPostgres.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(u) from UsersModel u", Long.class);
        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Override
    public UsersPostgres findUserById(Long id) {
        TypedQuery<UsersPostgres> query = entityManager.createQuery("select u from UsersModel u where " +
                "u.id=:id", UsersPostgres.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public UsersPostgres findUserByUsername(String username) {
        TypedQuery<UsersPostgres> query = entityManager.createQuery("select u from UsersModel u where " +
                "u.username=:username", UsersPostgres.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public void persist(UsersPostgres model) {
        entityManager.persist(model);
    }

    @Override
    public void update(UsersPostgres model) {
        Session session = entityManager.unwrap(Session.class);
        session.update(model);
    }
}
