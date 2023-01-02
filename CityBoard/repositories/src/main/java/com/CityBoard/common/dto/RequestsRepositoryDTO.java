package com.CityBoard.common.dto;

import com.CityBoard.common.dto.enums.RequestStatusRepo;
import com.CityBoard.common.dto.enums.RequestTypeRepo;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestsRepositoryDTO {
    private Long id;
    private RequestTypeRepo type;
    private RequestStatusRepo status;
    private Long authorId;
    private Long advertId;
}
