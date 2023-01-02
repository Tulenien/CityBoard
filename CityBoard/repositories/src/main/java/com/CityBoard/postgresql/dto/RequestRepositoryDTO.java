package com.CityBoard.postgresql.dto;

import com.CityBoard.dto.RequestDTO;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestRepositoryDTO implements RequestDTO {
    private Long id;
    private RequestType type;
    private RequestStatus status;
    private Long authorId;
    private Long advertId;
}
