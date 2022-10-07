package com.CityBoard.models.dto;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertDTO {
    private AdvertStatus status;
    private AdvertType type;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String street;
    private String house_code;
    private Integer flat_num;
    private Integer floor;
    private Integer floors;
    private Integer rooms_num;
    private Float area;
    private Float living_area;
    private Integer price;
    private String description;

    public Adverts mapDTOtoEntity() {
        Adverts advert = Adverts.builder()
                .type(type)
                .email(email)
                .phone(phone)
                .city(city)
                .district(district)
                .street(street)
                .house_code(house_code)
                .flat_num(flat_num)
                .floor(floor)
                .floors(floors)
                .rooms_num(rooms_num)
                .area(area)
                .living_area(living_area)
                .price(price)
                .description(description)
                .status(status)
                .build();
        return advert;
    }

    public void mapEntity(Adverts advert) {
        type = advert.getType();
        email = advert.getEmail();
        phone = advert.getPhone();
        city = advert.getCity();
        district = advert.getDistrict();
        street = advert.getStreet();
        house_code = advert.getHouse_code();
        flat_num = advert.getFlat_num();
        floor = advert.getFloor();
        floors = advert.getFloors();
        rooms_num = advert.getRooms_num();
        area = advert.getArea();
        living_area = advert.getLiving_area();
        price = advert.getPrice();
        description = advert.getDescription();
        status = advert.getStatus();
    }
}
