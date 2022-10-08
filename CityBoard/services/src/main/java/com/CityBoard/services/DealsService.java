package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.models.Deals;
import com.CityBoard.repositories.DealsRepository;
import org.springframework.stereotype.Service;

@Service
public class DealsService extends AbstractService<Deals, DealsRepository> {
    public DealsService(DealsRepository repository) {
        super(repository);
    }

    public Deals createDeal(Users seller, Users customer, Adverts advert) {
        Deals deal = Deals.builder()
                .advert(advert)
                .customer(customer)
                .seller(seller)
                .build();
        save(deal);
        return deal;
    }

    @Override
    public void save(Deals entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Deals entity) {
        repository.delete(entity);
    }
}
