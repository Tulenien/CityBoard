package com.CityBoard.services;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.repositories.AdvertsRepository;
import org.springframework.stereotype.Service;


@Service
public class AdvertsService extends AbstractService<Adverts, AdvertsRepository> {

    public AdvertsService(AdvertsRepository repository) {
        super(repository);
    }

    public Adverts createAdvert(Users user, AdvertDTO advertDTO) {
        Adverts advert = advertDTO.mapDTOtoEntity();
        advert.setStatus(AdvertStatus.VISIBLE);
        advert.setMod_check(false);
        advert.setUser(user);
        return advert;
    }

    public Adverts updateAdvert(Adverts advert, AdvertDTO newAdvert) {
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
        advert.setMod_check(false);
        return advert;
    }

    public void hideAdvert(Adverts advert) {
        advert.setStatus(AdvertStatus.HIDDEN);
    }

    public void deleteAdvert(Adverts advert) {
        advert.setStatus(AdvertStatus.DELETED);
    }

    public void doModeratorCheck(Adverts advert) {
        // TODO: add validation here
        advert.setMod_check(true);
    }

    public void deleteAdvertPermanently(Adverts advert) {
        delete(advert);
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
