package com.CityBoard.rest.data;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertFormData {
    private AdvertType type;
    private String email;
    private String phone;
    // Address
    private String city;
    private String district;
    private String street;
    private String houseCode;
    private Integer flatNumber;
    // Info
    private Integer floor;
    private Integer floors;
    private Integer roomsNumber;
    private Float area;
    private Float livingArea;
    private Integer price;
    private String description;

    public Adverts mapToAdverts() {
        Adverts advert = Adverts.builder()
                .type(type)
                .email(email)
                .phone(phone)
                .city(city)
                .district(district)
                .street(street)
                .houseCode(houseCode)
                .flatNumber(flatNumber)
                .floor(floor)
                .floors(floors)
                .roomsNumber(roomsNumber)
                .area(area)
                .livingArea(livingArea)
                .price(price)
                .description(description)
                .build();
        return advert;
    }
}
