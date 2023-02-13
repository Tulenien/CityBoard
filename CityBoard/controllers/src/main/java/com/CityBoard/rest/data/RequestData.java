package com.CityBoard.rest.data;

import com.CityBoard.models.enums.RequestType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestData {
    public RequestType type;
    public Long authorId;
    public Long advertId;
}
