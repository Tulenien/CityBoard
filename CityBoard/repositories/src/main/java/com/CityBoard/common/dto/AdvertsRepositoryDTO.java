package com.CityBoard.common.dto;

import com.CityBoard.common.dto.enums.AdvertStatusRepo;
import com.CityBoard.common.dto.enums.AdvertTypeRepo;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
