package com.CityBoard.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    List<Request> requests;
    @OneToMany(mappedBy = "user")
    List<Advert> adverts;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

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
        if (this.status != UserStatus.DELETED) {
            return true;
        }
        return false;
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
        if (this.status == UserStatus.ACTIVE) {
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public boolean isPassword_expired() {
        return password_expired;
    }

    public void setPassword_expired(boolean password_expired) {
        this.password_expired = password_expired;
    }
}
