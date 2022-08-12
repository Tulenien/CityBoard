package com.CityBoard.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RequestType type;
    private RequestStatus status;
    @CreationTimestamp
    private Timestamp created_at;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users user;
    @OneToOne
    @JoinColumn(name = "advert_id")
    private Advert advert;

    // Request actions
    public boolean isClosed() {
        if (this.status == RequestStatus.ACCEPTED || this.status == RequestStatus.REJECTED) {
            return true;
        }
        return false;
    }

    // Default getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }
}
