package com.CityBoard.application.repositories;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import com.CityBoard.repositories.AdvertsRepository;
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
class AdvertsRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AdvertsRepository repository;

    @Test
    void injectedComponents_areNotNull() {
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    @Sql(value = "classpath:testAdvertsData.sql")
    void findAll_shouldReturnCorrectNumberOfItems() {
        List<Adverts> adverts = repository.findAll();
        assertEquals(12, adverts.size());
    }

    @Test
    @Sql(value = "classpath:testAdvertsData.sql")
    void findByExactId_shouldReturnMatchingResult() {
        Adverts advert = repository.findById(1L).get();
        assertEquals(false, advert.isModCheck());
        assertEquals(AdvertType.RENT, advert.getType());
        assertEquals(AdvertStatus.VISIBLE, advert.getStatus());
    }

    @Test
    @Sql(value = "classpath:testAdvertsData.sql")
    void findByWrongId_shouldReturnEmptyData() {
        assertTrue(repository.findById(13L).isEmpty());
    }

    @Test
    @Sql(value = "classpath:testAdvertsData.sql")
    void findByType_eachTypeShouldReturnMatchingCountOfItems() {
        List<Adverts> adverts = repository.findByType(AdvertType.RENT);
        assertEquals(6, adverts.size());
        adverts = repository.findByType(AdvertType.SALE);
        assertEquals(6, adverts.size());
    }

    @Test
    @Sql(value = "classpath:testAdvertsData.sql")
    void findByStatus_eachStatusShouldReturnMatchingCountOfItems() {
        List<Adverts> adverts = repository.findByStatus(AdvertStatus.VISIBLE);
        assertEquals(4, adverts.size());
        adverts = repository.findByStatus(AdvertStatus.HIDDEN);
        assertEquals(4, adverts.size());
        adverts = repository.findByStatus(AdvertStatus.DELETED);
        assertEquals(4, adverts.size());
    }

    @Test
    @Sql(value = "classpath:testAdvertsData.sql")
    void findByModCheck_eachModCheckShouldReturnMatchingCountOfItems() {
        List<Adverts> adverts = repository.findByModCheck(false);
        assertEquals(6, adverts.size());
        adverts = repository.findByModCheck(true);
        assertEquals(6, adverts.size());
    }
}