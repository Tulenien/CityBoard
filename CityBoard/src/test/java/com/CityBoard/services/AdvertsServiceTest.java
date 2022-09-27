package com.CityBoard.services;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import com.CityBoard.repositories.AdvertsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AdvertsServiceTest {

    private AdvertsService advertsService;
    private AdvertsRepository advertsRepository;

    @BeforeEach
    public void setup() {
        advertsRepository = Mockito.mock(AdvertsRepository.class);
        advertsService = new AdvertsService(advertsRepository);
    }

    @Test
    void createdFromDtoAndBuilder_areEqual() {
        String username = "1";
        String password = "2";

        Users user = Users.builder()
                .username(username)
                .password(password)
                .build();
        Adverts expected = Adverts.builder()
                .user(user)
                .type(AdvertType.RENT)
                .mod_check(false)
                .status(AdvertStatus.VISIBLE)
                .build();
        AdvertDTO data = AdvertDTO.builder()
                .type(AdvertType.RENT)
                .status(AdvertStatus.HIDDEN)
                .build();

        Adverts actual = advertsService.createAdvert(user, data);
        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.isMod_check(), actual.isMod_check());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void createdFromDifferentUsers_areNotEqual() {
        String username = "1";
        String password = "2";

        Users user1 = Users.builder()
                .username(username)
                .password(password)
                .build();
        Users user2 = Users.builder()
                .username(password)
                .password(username)
                .build();
        AdvertDTO data = AdvertDTO.builder()
                .type(AdvertType.RENT)
                .status(AdvertStatus.HIDDEN)
                .build();

        Adverts expected = advertsService.createAdvert(user1, data);
        Adverts actual = advertsService.createAdvert(user2, data);
        assertNotEquals(expected.getUser(), actual.getUser());
        assertNotEquals(actual, expected);
    }

    @Test
    void AdvertsIsUpdated_shouldBecomeEqual() {
        AdvertType oldType = AdvertType.RENT;
        AdvertStatus oldStatus = AdvertStatus.VISIBLE;
        AdvertType newType = AdvertType.SALE;
        AdvertStatus newStatus = AdvertStatus.HIDDEN;

        Users user = Users.builder()
                .username("1")
                .password("2")
                .build();
        Adverts actual = Adverts.builder()
                .user(user)
                .type(oldType)
                .mod_check(true)
                .status(oldStatus)
                .build();
        AdvertDTO data = AdvertDTO.builder()
                .type(newType)
                .status(newStatus)
                .build();
        advertsService.updateAdvert(actual, data);
        assertEquals(actual.getType(), newType);
        assertEquals(actual.getStatus(), newStatus);
        assertEquals(actual.isMod_check(), false);
    }

    @Test
    void hideAdvert_shouldChangeStatus() {
        Adverts advert = new Adverts();
        advertsService.hideAdvert(advert);
        assertEquals(advert.getStatus(), AdvertStatus.HIDDEN);
    }

    @Test
    void deleteAdvert_shouldChangeStatus() {
        Adverts advert = new Adverts();
        advertsService.deleteAdvert(advert);
        assertEquals(advert.getStatus(), AdvertStatus.DELETED);
    }

    @Test
    void doModeratorCheck_shouldChangeMod_check() {
        Adverts advert = new Adverts();
        advertsService.doModeratorCheck(advert);
        assertEquals(advert.isMod_check(), true);
    }

    @Test
    void deleteAdvertPermanently_returnsNull() {
        Adverts advert = new Adverts();
        advert.setMod_check(true);
        advert.setType(AdvertType.SALE);
        advert.setStatus(AdvertStatus.VISIBLE);
        Mockito.doAnswer(invocationOnMock -> {
            Object arg0 = invocationOnMock.getArgument(0);

            assertNotNull(advert);
            return null;
        }).when(advertsRepository).delete(Mockito.any(Adverts.class));
        advertsService.deleteAdvertPermanently(advert);
    }

    @Test
    void getAdvertByCorrectId_shouldReturnAdvert() {
        Adverts advert = new Adverts();
        advert.setId(1L);
        advert.setDescription("The Chosen One");
        Long id = 1L;
        Mockito.when(advertsRepository.findById(id)).thenReturn(Optional.of(advert));
        assertEquals(advertsService.getAdvertById(id), advert);
    }

    @Test
    void getAdvertByIncorrectId_shouldReturnNull() {
        Adverts advert = new Adverts();
        advert.setId(1L);
        advert.setDescription("Not the Chosen One");
        Long correctId = 1L;
        Long wrongId = 2L;
        Mockito.when(advertsRepository.findById(correctId)).thenReturn(Optional.of(advert));
        Mockito.when(advertsRepository.findById(wrongId)).thenReturn(Optional.empty());
        assertNull(advertsService.getAdvertById(wrongId));
    }
}