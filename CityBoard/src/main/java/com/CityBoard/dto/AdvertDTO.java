package com.CityBoard.dto;

import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertDTO {
    private AdvertType type;
    private String email;
    private String phone;
    private Timestamp updated_at;
    private AdvertStatus status;
    private String address;
    private Integer price;
    private Float area;
}
