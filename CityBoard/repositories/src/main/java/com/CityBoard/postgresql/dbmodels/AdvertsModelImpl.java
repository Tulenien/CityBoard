package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "adverts")
public class AdvertsModel extends AbstractModel {
    private AdvertType type;
    private String email;
    private String phone;
    private AdvertStatus status;
    // Address
    private String city;
    private String district;
    private String street;
    @Column(name = "house_code")
    private String houseCode;
    @Column(name = "flat_num")
    private Integer flatNum;
    // Info
    private Integer floor;
    private Integer floors;
    @Column(name = "rooms_num")
    private Integer roomsNum;
    private Float area;
    @Column(name = "living_area")
    private Float livingArea;
    private Integer price;
    private String description;

    // Service info
    @Column(name = "mod_check")
    private boolean modCheck;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UsersModel user;
    @OneToMany(mappedBy = "advert")
    private List<RequestsModel> requests;

    public Adverts mapDTOtoEntity() {
        Adverts advert = Adverts.builder()
                .id(id)
                .type(type)
                .email(email)
                .phone(phone)
                .status(status)
                .city(city)
                .district(district)
                .street(street)
                .houseCode(houseCode)
                .flatNumber(flatNum)
                .floor(floor)
                .floors(floors)
                .roomsNumber(roomsNum)
                .area(area)
                .livingArea(livingArea)
                .price(price)
                .description(description)
                .authorId(user.getId())
                .build();
        return advert;
    }

    public void mapEntity(Adverts advert) {
        id = advert.getId();
        type = advert.getType();
        email = advert.getEmail();
        phone = advert.getPhone();
        status = advert.getStatus();
        city = advert.getCity();
        district = advert.getDistrict();
        street = advert.getStreet();
        houseCode = advert.getHouseCode();
        flatNum = advert.getFlatNumber();
        floor = advert.getFloor();
        floors = advert.getFloors();
        roomsNum = advert.getRoomsNumber();
        area = advert.getArea();
        livingArea = advert.getLivingArea();
        price = advert.getPrice();
        description = advert.getDescription();
        modCheck = advert.isModCheck();
    }
}
