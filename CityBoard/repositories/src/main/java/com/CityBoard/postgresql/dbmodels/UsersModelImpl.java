package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.interfaces.dbmodels.UsersModel;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UsersModelImpl extends AbstractModel implements UsersModel {
    @OneToMany(mappedBy = "user")
    List<RequestsModelImpl> requests;
    @OneToMany(mappedBy = "user")
    List<AdvertsModelImpl> adverts;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(length = 1000)
    private String password;
    private String name;git
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
                .status(status)
                .password_expired(password_expired)
                .roles(roles)
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
        status = user.getStatus();
        password_expired = user.isPassword_expired();
        roles = user.getRoles();
    }
}