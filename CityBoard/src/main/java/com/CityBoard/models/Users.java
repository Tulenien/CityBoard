package com.CityBoard.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = false)
    private String login;
    @Column(length = 1000)
    private String password;
    private String full_name;
    @CreationTimestamp
    private Timestamp created_at;
    private UserStatus status;
    @OneToMany(mappedBy = "users")
    List<Request> requests;
    @OneToMany(mappedBy = "users")
    List<Advert> adverts;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private HashSet<Roles> roles = new HashSet<>();

    // User actions
    public void banUser() {
        this.setStatus(UserStatus.BANNED);
    }

    public void restoreUser() {
        this.setStatus(UserStatus.USUAL);
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
        return roles.contains(Roles.MOD);
    }

    public boolean isAdmin() {
        return roles.contains(Roles.ADMIN);
    }

    // Default getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public HashSet<Roles> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<Roles> roles) {
        this.roles = roles;
    }
}
