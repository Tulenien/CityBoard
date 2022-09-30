package com.CityBoard.models;

import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    // Address
    private String city;
    private String district;
    private String street;
    private String house_code;
    private Integer flat_num;
    // Info
    private Integer floor;
    private Integer floors;
    private Integer rooms_num;
    private Float area;
    private Float living_area;
    private Integer price;
    private String description;

    // Service info
    private boolean mod_check = false;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users user = null;
    @OneToMany(mappedBy = "advert")
    private List<Requests> requests = null;
}
