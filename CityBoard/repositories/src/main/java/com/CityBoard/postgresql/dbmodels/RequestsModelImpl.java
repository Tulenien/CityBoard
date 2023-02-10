package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.postgresql.dbmodels.enums.RequestStatusPostgres;
import com.CityBoard.postgresql.dbmodels.enums.RequestTypePostgres;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "requests")
public class RequestsModelImpl extends AbstractModel {
    @Enumerated(EnumType.ORDINAL)
    private RequestTypePostgres type;
    @Enumerated(EnumType.ORDINAL)
    private RequestStatusPostgres status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UsersModelImpl user;
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private AdvertsModelImpl advert;
}