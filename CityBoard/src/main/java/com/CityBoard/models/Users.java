package com.CityBoard.models;

import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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

    // User actions
    public void banUser() {
        this.setStatus(UserStatus.BANNED);
    }

    public void restoreUser() {
        this.setStatus(UserStatus.LOGGED_OFF);
    }

    public void deleteUser() {
        this.setStatus(UserStatus.DELETED);
    }

    // Role actions
    public void addRole(Roles role) {
        roles.add(role);
    }

    public void removeRole(Roles role) {
        roles.remove(role);
    }

    public boolean isMod() {
        return roles.contains(Roles.ROLE_MOD);
    }

    public boolean isAdmin() {
        return roles.contains(Roles.ROLE_ADMIN);
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // Default getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Requests> getRequests() {
        return requests;
    }

    public void setRequests(List<Requests> requests) {
        this.requests = requests;
    }

    public List<Adverts> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Adverts> adverts) {
        this.adverts = adverts;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void setPassword_expired(boolean password_expired) {
        this.password_expired = password_expired;
    }
}
