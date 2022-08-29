package com.CityBoard.repositories;

import com.CityBoard.models.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    public List<Advert> findByAuthor(String author);
}
