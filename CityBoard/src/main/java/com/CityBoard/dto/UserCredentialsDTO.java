package com.CityBoard.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCredentialsDTO {
    private String username;
    private String password;
}
