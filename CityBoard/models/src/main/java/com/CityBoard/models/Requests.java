package com.CityBoard.models;

import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Requests {
    private Long id;
    private RequestType type;
    private RequestStatus status;
    private Long authorId;
    private Long advertId;
}
