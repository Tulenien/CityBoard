package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.postgresql.dbmodels.enums.RequestStatusPostgres;
import com.CityBoard.postgresql.dbmodels.enums.RequestTypePostgres;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "requests")
public class RequestsPostgres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;

    @Enumerated(EnumType.ORDINAL)
    private RequestTypePostgres type;
    @Enumerated(EnumType.ORDINAL)
    private RequestStatusPostgres status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UsersPostgres user;
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private AdvertsPostgres advert;
}