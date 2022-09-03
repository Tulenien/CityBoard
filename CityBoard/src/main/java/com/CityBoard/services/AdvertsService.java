package com.CityBoard.services;

import com.CityBoard.models.*;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import com.CityBoard.repositories.AdvertsRepository;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertsService extends AbstractService<Adverts, AdvertsRepository> {
    private final UsersRepository usersRepository;

    public AdvertsService(AdvertsRepository repository, UsersRepository usersRepository) {
        super(repository);
        this.usersRepository = usersRepository;
    }

    public Adverts createAdvert(String authorName, AdvertType advertType, String email,
                                String phone, Integer price, String address,
                                Float area) {
        Users user = findAdvertAuthor(authorName);
        Adverts advert = Adverts.builder()
                .type(advertType)
                .email(email)
                .phone(phone)
                .price(price)
                .address(address)
                .area(area)
                .user(user)
                .status(AdvertStatus.VISIBLE)
                .mod_check(false)
                .build();
        save(advert);
        updateUserAdverts(user, advert);
        return advert;
    }

    public Users findAdvertAuthor(String authorName) {
        return usersRepository.findByUsername(authorName);
    }

    public void updateUserAdverts(Users user, Adverts advert) {
        user.addAdvert(advert);
        usersRepository.save(user);
    }

    public Adverts updateAdvert(Adverts toUpdate, AdvertType advertType, String email,
                               String phone, Integer price, String address,
                               Float area, AdvertStatus advertStatus) {
        toUpdate.setType(advertType);
        toUpdate.setEmail(email);
        toUpdate.setPhone(phone);
        toUpdate.setStatus(advertStatus);
        toUpdate.setMod_check(false);
        toUpdate.setAddress(address);
        toUpdate.setPrice(price);
        toUpdate.setArea(area);
        return toUpdate;
    }

    public void checkAdvert(Adverts toCheck) {
        toCheck.setMod_check(true);
    }

    public void hideAdvert(Adverts toHide) {
        toHide.setStatus(AdvertStatus.HIDDEN);
    }

    public void deleteAdvert(Adverts toDelete) {
        toDelete.setStatus(AdvertStatus.DELETED);
    }

    public List<Adverts> getAllAdverts() {
        return repository.findAll();
    }

    public Adverts getAdvertById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Adverts> getUserAdverts(String authorName) {
        Users user = usersRepository.findByUsername(authorName);
        return user.getAdverts();
    }

    @Override
    public void save(Adverts entity) {
        repository.save(entity);
    }
}
