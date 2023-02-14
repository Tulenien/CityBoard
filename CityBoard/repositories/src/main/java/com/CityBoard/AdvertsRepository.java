package com.CityBoard;

import com.CityBoard.models.Adverts;
import com.CityBoard.postgresql.dto.AdvertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class AdvertsRepository implements IAdvertsRepository {
    @Override
    public Page<Adverts> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Adverts> findAllVisiblePaginated(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Adverts> findAllNotDeletedPaginated(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Adverts> findVisibleNotAuthoredPaginated(Long authorId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Adverts> findAllAuthored(Long authorId) {
        return null;
    }

    @Override
    public Optional<AdvertDTO> findById(Long id) {
        return Optional.empty();
    }
}
