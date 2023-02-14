package com.CityBoard;

import com.CityBoard.models.Adverts;
import com.CityBoard.postgresql.dto.AdvertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAdvertsRepository {
    Page<Adverts> findAll(Pageable pageable);

    Page<Adverts> findAllVisiblePaginated(Pageable pageable);

    Page<Adverts> findAllNotDeletedPaginated(Pageable pageable);

    Page<Adverts> findVisibleNotAuthoredPaginated(Long authorId, Pageable pageable);

    List<Adverts> findAllAuthored(Long authorId);

    Optional<AdvertDTO> findById(Long id);
}
