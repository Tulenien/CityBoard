package com.CityBoard.dto;

import com.CityBoard.models.enums.AdvertType;

public class AdvertPostDTO {
    // Contact info
    private String email;
    private String phone;
    // Address
    private String city;
    private String district;
    private String street;
    private String houseCode;
    private Integer flatNum;
    // Realty info
    private Integer floor;
    private Integer floors;
    private Integer roomsNum;
    private Float area;
    private Float livingArea;
    private Integer price;
    private String description;
    // Service info
    private AdvertType type;
}
