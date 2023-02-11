package com.CityBoard.repository;

import com.CityBoard.repository.enums.RolesRepo;
import com.CityBoard.repository.enums.UserStatusRepo;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersRepositoryDTO {
    private Long id;
    private String username;
    private String password;
    private Set<RolesRepo> roles;
    private UserStatusRepo status;
    private boolean password_expired;
    private String name;
    private String surname;
    private String middle_name;
}
