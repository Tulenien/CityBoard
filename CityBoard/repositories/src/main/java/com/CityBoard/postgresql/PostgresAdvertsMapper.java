package com.CityBoard.postgresql;

import com.CityBoard.entities.enums.AdvertType;
import com.CityBoard.postgresql.dbmodels.AdvertsPostgres;
import com.CityBoard.postgresql.dbmodels.UsersPostgres;
import com.CityBoard.postgresql.dbmodels.enums.AdvertStatusPostgres;
import com.CityBoard.postgresql.dbmodels.enums.AdvertTypePostgres;
import com.CityBoard.repository.AdvertsRepositoryDTO;
import com.CityBoard.repository.enums.AdvertStatusRepo;
import com.CityBoard.repository.enums.AdvertTypeRepo;
//private Long id;
//private AdvertTypeRepo type;
//private AdvertStatusRepo status;
//private boolean modCheck;
//private Long authorId;
//private String email;
//private String phone;
//private String city;
//private String district;
//private String street;
//private String houseCode;
//private Integer flatNumber;
//private Integer floor;
//private Integer floors;
//private Integer roomsNumber;
//private Float area;
//private Float livingArea;
//private Integer price;
//private String description;

public class PostgresAdvertsMapper {

    static AdvertTypePostgres ATRtoATP (AdvertTypeRepo value) {
        return AdvertTypePostgres.values()[value.ordinal()];
    }

    static AdvertTypeRepo ATPtoATR (AdvertTypePostgres value) {
        return AdvertTypeRepo.values()[value.ordinal()];
    }

    static AdvertStatusRepo ASPtoASR (AdvertStatusPostgres value) {
        return AdvertStatusRepo.values()[value.ordinal()];
    }

    static AdvertStatusPostgres ASRtoASP (AdvertStatusRepo value) {
        return AdvertStatusPostgres.values()[value.ordinal()];
    }

    public AdvertsRepositoryDTO mapToDto(AdvertsPostgres advert) {
        AdvertsRepositoryDTO result = AdvertsRepositoryDTO.builder()
                .id(advert.getId())
                .type(ATPtoATR(advert.getType()))
                .status(ASPtoASR(advert.getStatus()))
                .modCheck(advert.isModCheck())
                .authorId(advert.getUser().getId())
                .email(advert.getEmail())
                .phone(advert.getPhone())
                .city(advert.getCity())
                .district(advert.getDistrict())
                .street(advert.getStreet())
                .houseCode(advert.getHouseCode())
                .flatNumber(advert.getFlatNum())
                .floor(advert.getFloor())
                .floors(advert.getFloors())
                .roomsNumber(advert.getRoomsNum())
                .area(advert.getArea())
                .livingArea(advert.getLivingArea())
                .price(advert.getPrice())
                .description(advert.getDescription())
                .build();
        return result;
    }

    public AdvertsPostgres mapToModel(AdvertsRepositoryDTO advert) {
        UsersPostgres author = UsersPostgres.builder()
                .id(advert.getAuthorId())
                .build();

        AdvertsPostgres result = AdvertsPostgres.builder()
                .id(advert.getId())
                .type(ATRtoATP(advert.getType()))
                .status(ASRtoASP(advert.getStatus()))
                .modCheck(advert.isModCheck())
                .user(author)
                .email(advert.getEmail())
                .phone(advert.getPhone())
                .city(advert.getCity())
                .district(advert.getDistrict())
                .street(advert.getStreet())
                .houseCode(advert.getHouseCode())
                .flatNum(advert.getFlatNumber())
                .floor(advert.getFloor())
                .floors(advert.getFloors())
                .roomsNum(advert.getRoomsNumber())
                .area(advert.getArea())
                .livingArea(advert.getLivingArea())
                .price(advert.getPrice())
                .description(advert.getDescription())
                .build();
        return result;
    }
}
