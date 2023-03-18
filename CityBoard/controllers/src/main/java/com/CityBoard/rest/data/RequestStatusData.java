package com.CityBoard.rest.data;

import com.CityBoard.models.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStatusData {
    public Long person;
    public RequestStatus status;
}