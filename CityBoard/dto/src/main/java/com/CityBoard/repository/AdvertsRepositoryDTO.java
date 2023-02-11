package com.CityBoard.repository;

import com.CityBoard.repository.enums.AdvertStatusRepo;
import com.CityBoard.repository.enums.AdvertTypeRepo;
import lombok.*;

@Builder
@Data
public class AdvertsRepositoryDTO {
    private Long id;
    private AdvertTypeRepo type;
    private AdvertStatusRepo status;
    private boolean modCheck;
    private Long authorId;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String street;
    private String houseCode;
    private Integer flatNumber;
    private Integer floor;
    private Integer floors;
    private Integer roomsNumber;
    private Float area;
    private Float livingArea;
    private Integer price;
    private String description;
}
