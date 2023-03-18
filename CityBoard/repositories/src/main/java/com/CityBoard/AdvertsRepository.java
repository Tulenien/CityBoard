package com.CityBoard;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.postgresql.repository.AdvertsJPARepository;
import com.CityBoard.postgresql.repository.UsersJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdvertsRepository implements IAdvertsRepository {
    private final AdvertsJPARepository advertsJPARepository;
    private final UsersJPARepository usersJPARepository;

    public AdvertsRepository(AdvertsJPARepository advertsJPARepository, UsersJPARepository usersJPARepository) {
        this.advertsJPARepository = advertsJPARepository;
        this.usersJPARepository = usersJPARepository;
    }

    @Override
    public Page<Adverts> findAll(Pageable pageable) {
        Page<AdvertDTO> dtoPage = advertsJPARepository.findAll(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    @Override
    public Page<Adverts> findAllVisiblePaginated(Pageable pageable) {
        Page<AdvertDTO> dtoPage = advertsJPARepository.findAllVisiblePaginated(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    @Override
    public Page<Adverts> findAllNotDeletedPaginated(Pageable pageable) {
        Page<AdvertDTO> dtoPage = advertsJPARepository.findAllNotDeletedPaginated(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    @Override
    public Page<Adverts> findVisibleNotAuthoredPaginated(Long authorId, Pageable pageable) {
        Page<AdvertDTO> dtoPage = advertsJPARepository.findVisibleNotAuthoredPaginated(authorId, pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    @Override
    public List<Adverts> findAllAuthored(Long authorId) {
        List<AdvertDTO> dtoList = advertsJPARepository.findAllAuthored(authorId);
        return mapDTOtoEntityList(dtoList);
    }

    @Override
    public Adverts findById(Long id) {
        AdvertDTO dto = advertsJPARepository.findById(id).orElse(null);
        if (dto != null) {
            return dto.mapDTOtoEntity();
        }
        return null;
    }

    public void saveAdvert(Adverts advert, Long authorId) {
        AdvertDTO dto = new AdvertDTO();
        Long createdId = dto.getId();

        dto.mapEntity(advert);
        dto.setId(createdId);
        dto.setStatus(AdvertStatus.VISIBLE);
        dto.setModCheck(false);
        dto.setUser(usersJPARepository.findById(authorId).orElse(null));
        advertsJPARepository.save(dto);
    }

    public void updateAdvert(Adverts advert) {
        AdvertDTO dto = advertsJPARepository.findById(advert.getId()).orElse(null);
        if (dto != null) {
            dto.mapEntity(advert);
            dto.setModCheck(false);
            advertsJPARepository.save(dto);
        }
    }

    public void deleteAdvert(Adverts advert) {
        advertsJPARepository.deleteById(advert.getId());
    }

    @Override
    public boolean changeStatus(Long id, AdvertStatus status) {
        AdvertDTO dto = advertsJPARepository.findById(id).orElse(null);
        if (dto != null) {
            dto.setStatus(status);
            advertsJPARepository.save(dto);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeModCheck(Long id) {
        AdvertDTO dto = advertsJPARepository.findById(id).orElse(null);
        if (dto != null) {
            dto.setModCheck(!dto.isModCheck());
            advertsJPARepository.save(dto);
            return true;
        }
        return false;
    }

    private Page<Adverts> mapDTOtoEntityPage(Page<AdvertDTO> dtoPage, Pageable pageable) {
        List<AdvertDTO> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Adverts> advertsList = new ArrayList<>();
        for (AdvertDTO dto : dtoList) {
            advertsList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(advertsList, pageable, totalElements);
    }

    private List<Adverts> mapDTOtoEntityList(List<AdvertDTO> dtoList) {
        List<Adverts> adverts = new ArrayList<>();
        for (AdvertDTO dto : dtoList) {
            adverts.add(dto.mapDTOtoEntity());
        }
        return adverts;
    }


}
