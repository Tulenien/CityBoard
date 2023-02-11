package com.CityBoard.entities;

import com.CityBoard.entities.enums.RequestStatus;
import com.CityBoard.entities.enums.RequestType;
import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;

@Data
public class Requests {
    private Long id;
    private RequestType type;
    private RequestStatus status;
    private Long authorId;
    private Long advertId;
}
