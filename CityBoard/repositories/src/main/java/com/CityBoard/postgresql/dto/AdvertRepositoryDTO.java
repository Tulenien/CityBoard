package com.CityBoard.postgresql.dto;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdvertRepositoryDTO implements AdvertDTO {
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
    private Long id;
    private AdvertType type;
    private AdvertStatus status;
    private boolean modCheck;
    private Long authorId;
}
