package com.CityBoard.models;

import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String middle_name;
    private UserStatus status;
    private boolean password_expired;
    private Set<Roles> roles;

    @JsonIgnore
    public Set<Roles> getUnusedRoles() {
        Set<Roles> allRoles = new HashSet<>();
        allRoles.add(Roles.ROLE_USER);
        allRoles.add(Roles.ROLE_MOD);
        allRoles.add(Roles.ROLE_ADMIN);
        allRoles.removeAll(roles);
        return allRoles;
    }
}
