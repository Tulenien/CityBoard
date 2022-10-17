package com.CityBoard.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deals extends AbstractEntity {
    @OneToOne
    private Adverts advert = null;

    @OneToOne
    private Users seller = null;

    @OneToOne
    private Users customer = null;
}
