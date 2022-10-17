package com.CityBoard.models.dto;

import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEncodedDTO {
    private String username;
    private String encodedPassword;
    private UserStatus userStatus;
    private Set<Roles> roles;
    private boolean password_expired;
}
