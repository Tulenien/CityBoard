package com.CityBoard.models;

import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Adverts {
    private Long id;
    private AdvertType type;
    private String email;
    private String phone;
    private AdvertStatus status;
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

    // Service info
    private boolean modCheck;

    private Long authorId;
}
