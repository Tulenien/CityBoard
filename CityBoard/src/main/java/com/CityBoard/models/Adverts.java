package com.CityBoard.models;

import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Adverts extends AbstractEntity {
    private AdvertType type;
    private String email;
    private String phone;
    private AdvertStatus status;
    private boolean mod_check = false;
    private String address;
    private Integer price;
    private Float area;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users user = null;
    @OneToMany(mappedBy = "advert")
    private List<Requests> requests = null;
}
