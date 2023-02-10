package com.CityBoard.entities;

import com.CityBoard.entities.enums.AdvertStatus;
import com.CityBoard.entities.enums.AdvertType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Adverts {
    // Service info
    private Long id;
    private AdvertType type;
    private AdvertStatus status;
    private boolean modCheck;
    private Long authorId;
    // Contact info
    private String email;
    private String phone;
    // Address
    private String city;
    private String district;
    private String street;
    private String houseCode;
    private Integer flatNumber;
    // Realty info
    private Integer floor;
    private Integer floors;
    private Integer roomsNumber;
    private Float area;
    private Float livingArea;
    private Integer price;
    private String description;
}
