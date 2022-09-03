package com.CityBoard.models;

import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Requests extends AbstractEntity{
    private RequestType type;
    private RequestStatus status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users user = null;
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Adverts advert = null;

    // Request actions
    public boolean isClosed() {
        if (this.status == RequestStatus.ACCEPTED || this.status == RequestStatus.REJECTED) {
            return true;
        }
        return false;
    }

    // Default getters and setters

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Adverts getAdvert() {
        return advert;
    }

    public void setAdvert(Adverts advert) {
        this.advert = advert;
    }
}
