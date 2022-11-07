package com.CityBoard.postgresql.repository;

import com.CityBoard.postgresql.dbmodels.UsersModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends org.springframework.data.jpa.repository.JpaRepository<UsersModel, Long> {
    Page<UsersModel> findAll(Pageable pageable);

    UsersModel findByUsername(String username);

    Optional<UsersModel> findById(Long id);
}
