package com.CityBoard.common.dto;

import com.CityBoard.common.dto.enums.RolesRepo;
import com.CityBoard.common.dto.enums.UserStatusRepo;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
