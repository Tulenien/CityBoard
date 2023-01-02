package com.CityBoard.postgresql.dto;

import com.CityBoard.dto.UserDTO;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRepositoryDTO implements UserDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String middleName;
    private UserStatus status;
    private boolean passwordExpired;
    private Set<Roles> roles;
}
