package com.CityBoard.repository;

import com.CityBoard.repository.enums.RequestStatusRepo;
import com.CityBoard.repository.enums.RequestTypeRepo;
import lombok.*;

@Data
public class RequestsRepositoryDTO {
    private Long id;
    private RequestTypeRepo type;
    private RequestStatusRepo status;
    private Long authorId;
    private Long advertId;
}
