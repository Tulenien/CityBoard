package com.CityBoard.models;

import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Requests extends AbstractEntity {
    private RequestType type;
    private RequestStatus status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users user = null;
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Adverts advert = null;

    public boolean isClosed() {
        if (this.status == RequestStatus.ACCEPTED || this.status == RequestStatus.REJECTED) {
            return true;
        }
        return false;
    }
}
