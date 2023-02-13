package com.CityBoard.postgresql.dto;

import com.CityBoard.interfaces.AbstractEntityDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "jwttokens")
public class JwtToken extends AbstractEntityDTO {
    @Column(unique = true, updatable = false)
    private String username;
    @Column(length = 1000)
    private String token;
}
