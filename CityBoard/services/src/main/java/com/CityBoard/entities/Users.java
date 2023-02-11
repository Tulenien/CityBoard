package com.CityBoard.entities;

import com.CityBoard.entities.enums.Roles;
import com.CityBoard.entities.enums.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class Users implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Set<Roles> roles;
    private UserStatus status;
    private boolean password_expired;
    // User info
    private String name;
    private String surname;
    private String middle_name;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (status == UserStatus.ACTIVE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !password_expired;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    //@JsonIgnore
    //public Set<Roles> getUnusedRoles() {
    //    Set<Roles> allRoles = new HashSet<>();
    //    for (Roles role : Roles.values()) {
    //        allRoles.add(role);
    //    }
    //    allRoles.removeAll(roles);
    //    return allRoles;
    //}
}
