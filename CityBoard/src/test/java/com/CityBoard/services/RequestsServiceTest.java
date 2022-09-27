package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.AdvertType;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.repositories.RequestsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestsServiceTest {


    private RequestsService requestsService;
    private RequestsRepository repository;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(RequestsRepository.class);
        requestsService = new RequestsService(repository);
    }

    @Test
    void createdFromDtoAndBuilder_areEqual() {
        String username = "1";
        String password = "2";

        Users user = Users.builder()
                .username(username)
                .password(password)
                .build();
        Adverts advert = Adverts.builder()
                .type(AdvertType.RENT)
                .mod_check(true)
                .build();
        Requests expected = Requests.builder()
                .user(user)
                .advert(advert)
                .type(RequestType.EVALUATION)
                .status(RequestStatus.PENDING)
                .build();
        RequestType type = RequestType.EVALUATION;

        Requests actual = requestsService.createRequest(user, advert, type);
        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getAdvert(), actual.getAdvert());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void acceptRequest_changesStatusToACCEPTED() {
        Requests request = new Requests();
        request.setStatus(RequestStatus.PENDING);
        requestsService.acceptRequest(request);
        assertEquals(request.getStatus(), RequestStatus.ACCEPTED);
    }

    @Test
    void rejectRequest_changesStatusToREJECTED() {
        Requests request = new Requests();
        request.setStatus(RequestStatus.PENDING);
        requestsService.rejectRequest(request);
        assertEquals(request.getStatus(), RequestStatus.REJECTED);
    }
}