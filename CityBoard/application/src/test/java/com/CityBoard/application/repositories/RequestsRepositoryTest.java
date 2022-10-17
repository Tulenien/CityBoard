package com.CityBoard.application.repositories;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.repositories.RequestsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RequestsRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RequestsRepository repository;

    @Test
    void injectedComponents_areNotNull() {
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findAll_shouldReturnCorrectNumberOfItems() {
        List<Requests> requests = repository.findAll();
        assertEquals(12, requests.size());
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findByExactId_shouldReturnMatchingResult() {
        Requests rentViewingAcceptedRequest = repository.findById(5L).get();
        assertEquals(RequestType.VIEWING, rentViewingAcceptedRequest.getType());
        assertEquals(RequestStatus.ACCEPTED, rentViewingAcceptedRequest.getStatus());
        assertEquals(1L, rentViewingAcceptedRequest.getAdvert().getId());
        assertEquals(2L, rentViewingAcceptedRequest.getUser().getId());
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findByWrongId_shouldReturnEmptyData() {
        assertTrue(repository.findById(13L).isEmpty());
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findBySeveralUsers_eachUserShouldReturnMatchingCountOfItems() {
        Users user = new Users();
        user.setId(1L);
        List<Requests> requests = repository.findByUser(user);
        assertEquals(4, requests.size());
        user.setId(2L);
        requests = repository.findByUser(user);
        assertEquals(3, requests.size());
        user.setId(3L);
        requests = repository.findByUser(user);
        assertEquals(5, requests.size());
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findByNotExistingUser_shouldReturnEmptyData() {
        Users user = new Users();
        user.setId(4L);
        List<Requests> requests = repository.findByUser(user);
        assertTrue(requests.isEmpty());
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findBySeveralAdverts_eachUserShouldReturnMatchingCountOfItems() {
        Adverts advert = new Adverts();
        advert.setId(1L);
        List<Requests> requests = repository.findByAdvert(advert);
        assertEquals(4, requests.size());
        advert.setId(2L);
        requests = repository.findByAdvert(advert);
        assertEquals(4, requests.size());
        advert.setId(3L);
        requests = repository.findByAdvert(advert);
        assertEquals(4, requests.size());
    }

    @Test
    @Sql(value = "classpath:testRequestsData.sql")
    void findByNotExistingAdvert_shouldReturnEmptyData() {
        Adverts advert = new Adverts();
        advert.setId(5L);
        List<Requests> requests = repository.findByAdvert(advert);
        assertTrue(requests.isEmpty());
    }
}