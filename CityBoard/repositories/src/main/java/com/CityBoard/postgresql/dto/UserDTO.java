package com.CityBoard.postgresql.dto;

import com.CityBoard.interfaces.AbstractEntityDTO;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserDTO extends AbstractEntityDTO implements UserDetails {
    @OneToMany(mappedBy = "user")
    List<RequestDTO> requests;
    @OneToMany(mappedBy = "user")
    List<AdvertDTO> adverts;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(length = 1000)
    private String password;
    private String name;
    private String surname;
    private String middle_name;
    private UserStatus status;
    private boolean password_expired;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public Users mapDTOtoEntity() {
        Users user = Users.builder()
                .id(id)
                .username(username)
                .password(password)
                .name(name)
                .surname(surname)
                .middle_name(middle_name)
                .password_expired(password_expired)
                .build();
        return user;
    }

    public void mapEntity(Users user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        name = user.getName();
        surname = user.getSurname();
        middle_name = user.getMiddle_name();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.status != UserStatus.BANNED) {
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
        if (this.status != UserStatus.DELETED) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}