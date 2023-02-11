package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.postgresql.dbmodels.enums.AdvertStatusPostgres;
import com.CityBoard.postgresql.dbmodels.enums.AdvertTypePostgres;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Builder
@Data
@Table(name = "adverts")
public class AdvertsPostgres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;

    @Enumerated(EnumType.ORDINAL)
    private AdvertTypePostgres type;
    private String email;
    private String phone;
    @Enumerated(EnumType.ORDINAL)
    private AdvertStatusPostgres status;
    // Address
    private String city;
    private String district;
    private String street;
    @Column(name = "house_code")
    private String houseCode;
    @Column(name = "flat_num")
    private Integer flatNum;
    // Info
    private Integer floor;
    private Integer floors;
    @Column(name = "rooms_num")
    private Integer roomsNum;
    private Float area;
    @Column(name = "living_area")
    private Float livingArea;
    private Integer price;
    private String description;

    // Service info
    @Column(name = "mod_check")
    private boolean modCheck;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UsersPostgres user;

    @OneToMany(mappedBy = "advert")
    private List<RequestsPostgres> requests;
}
