package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "requests")
public class RequestsModel extends AbstractModel {
    private RequestType type;
    private RequestStatus status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UsersModel user;
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private AdvertsModel advert;

    public Requests mapDTOtoEntity() {
        Requests request = Requests.builder()
                .id(id)
                .type(type)
                .status(status)
                .authorId(user.getId())
                .advertId(advert.getId())
                .build();
        return request;
    }

    public void mapEntity(Requests request) {
        id = request.getId();
        type = request.getType();
        status = request.getStatus();
    }
}