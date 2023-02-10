package com.CityBoard.postgresql.dbmodels;

import com.CityBoard.postgresql.dbmodels.enums.RolesPostgres;
import com.CityBoard.postgresql.dbmodels.enums.UserStatusPostgres;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class UsersModelImpl extends AbstractModel {
    @OneToMany(mappedBy = "user")
    List<RequestsModelImpl> requests;
    @OneToMany(mappedBy = "user")
    List<AdvertsModelImpl> adverts;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(length = 1000)
    private String password;
    private String name;
    private String surname;
    private String middle_name;
    @Enumerated(EnumType.ORDINAL)
    private UserStatusPostgres status;
    private boolean password_expired;
    @ElementCollection(targetClass = RolesPostgres.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<RolesPostgres> roles;
}