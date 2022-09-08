package com.CityBoard.models;

import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users extends AbstractEntity implements UserDetails {
    @Column(unique = true, updatable = false)
    private String username;
    @Column(length = 1000)
    private String password;
    private String full_name;
    @CreationTimestamp
    private Timestamp created_at;
    private UserStatus status;
    private boolean password_expired;
    @OneToMany(mappedBy = "user")
    List<Requests> requests = null;
    @OneToMany(mappedBy = "user")
    List<Adverts> adverts = null;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = null;

    public void addAdvert(Adverts advert) {
        adverts.add(advert);
    }

    public void addRequest(Requests request) {
        requests.add(request);
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
