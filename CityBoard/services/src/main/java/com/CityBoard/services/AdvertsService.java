package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.models.dto.AdvertDTO;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.repositories.AdvertsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdvertsService extends AbstractService<Adverts, AdvertsRepository> {
    private static final Logger logger = LoggerFactory.getLogger(AdvertsService.class);

    public AdvertsService(AdvertsRepository repository) {
        super(repository);
    }

    public Adverts createAdvert(Users user, AdvertDTO advertDTO) {
        Adverts advert = advertDTO.mapDTOtoEntity();
        advert.setStatus(AdvertStatus.VISIBLE);
        advert.setModCheck(false);
        advert.setUser(user);
        return advert;
    }

    public Adverts updateAdvert(Long advertId, AdvertDTO newAdvert) {
        Adverts advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            advert.setType(newAdvert.getType());
            advert.setEmail(newAdvert.getEmail());
            advert.setPhone(newAdvert.getPhone());
            advert.setStatus(newAdvert.getStatus());
            advert.setCity(newAdvert.getCity());
            advert.setDistrict(newAdvert.getDistrict());
            advert.setStreet(newAdvert.getStreet());
            advert.setHouse_code(newAdvert.getHouse_code());
            advert.setFlat_num(newAdvert.getFlat_num());
            advert.setFloor(newAdvert.getFloor());
            advert.setFloors(newAdvert.getFloors());
            advert.setRooms_num(newAdvert.getRooms_num());
            advert.setArea(newAdvert.getArea());
            advert.setLiving_area(newAdvert.getLiving_area());
            advert.setPrice(newAdvert.getPrice());
            advert.setDescription(newAdvert.getDescription());
            advert.setModCheck(false);
            save(advert);
        }
        return advert;
    }

    public List<Adverts> getAvailableAdverts() {
        List<Adverts> adverts = repository.findAllVisible();
        if (adverts.isEmpty()) {
            logger.error("Nothing in [AdvertsRepository]");
            return null;
        }
        return adverts;
    }

    public List<Adverts> getAuthoredAdverts(Users user) {
        List<Adverts> adverts = repository.findAllAuthored(user.getUsername());
        if (adverts.isEmpty()) {
            return null;
        }
        return adverts;
    }

    public List<Adverts> getAvailableAdvertsNotAuthored(Users user) {
        List<Adverts> adverts = repository.findAllVisibleNotAuthored(user.getUsername());
        if (adverts.isEmpty()) {
            logger.error("Nothing but author adverts in [AdvertsRepository], author: {}", user.getUsername());
            return null;
        }
        return adverts;
    }

    public void hideAdvert(Adverts advert) {
        logger.info("Advert hidden, id: {}", advert.getId());
        advert.setStatus(AdvertStatus.HIDDEN);
    }

    public void deleteAdvert(Adverts advert) {
        logger.info("Advert deleted softly, id: {}", advert.getId());
        advert.setStatus(AdvertStatus.DELETED);
    }

    public void doModeratorCheck(Adverts advert) {
        logger.warn("Advert checked, id: {}", advert.getId());
        advert.setModCheck(true);
        save(advert);
    }

    public void deleteAdvertPermanently(Adverts advert) {
        logger.warn("Advert hidden, id: {}", advert.getId());
        delete(advert);
    }

    public Adverts getAdvertById(Long advertId) {
        Optional<Adverts> advert = repository.findById(advertId);
        if (advert.isEmpty()) {
            logger.warn("Query with not existing advert id, id: {}", advertId);
            return null;
        }
        return advert.orElse(null);
    }

    @Override
    public void delete(Adverts entity) {
        repository.delete(entity);
    }

    @Override
    public void save(Adverts entity) {
        repository.save(entity);
    }
}
