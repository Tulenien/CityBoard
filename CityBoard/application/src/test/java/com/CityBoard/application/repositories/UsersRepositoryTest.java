package com.CityBoard.application.repositories;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UsersRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsersRepository repository;

    @Test
    void injectedComponents_areNotNull() {
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    @Sql(value = "classpath:testUsersData.sql")
    void findAll_shouldReturnCorrectNumberOfItems() {
        List<Users> users = repository.findAll();
        assertEquals(7, users.size());
    }

    @Test
    @Sql(value = "classpath:testUsersData.sql")
    void findByCorrectUsername_shouldReturnMatchingUser() {
        Users user = repository.findByUsername("user1");
        assertEquals(1L, user.getId());
        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ROLE_USER);
        roles.add(Roles.ROLE_MOD);
        roles.add(Roles.ROLE_ADMIN);
        assertTrue(user.getRoles().containsAll(roles));
    }

    @Test
    @Sql(value = "classpath:testUsersData.sql")
    void findByCorrectUsername_shouldReturnEmptyData() {
        Users user = repository.findByUsername("user8");
        assertNull(user);

    }

    @Test
    @Sql(value = "classpath:testUsersData.sql")
    void findByCorrectId_shouldReturnMatchingUser() {
        Users user = repository.findById(2L).get();
        assertEquals("user2", user.getUsername());
        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ROLE_USER);
        roles.add(Roles.ROLE_MOD);
        assertTrue(user.getRoles().containsAll(roles));
    }

    @Test
    @Sql(value = "classpath:testUsersData.sql")
    void findByCorrectId_shouldReturnEmptyData() {
        assertTrue(repository.findById(8L).isEmpty());
    }

    @Test
    @Sql(value = "classpath:testUsersData.sql")
    void findByEachRole_shouldReturnMatchingCountOfItems() {
        List<Users> users = repository.findByRoles(Roles.ROLE_USER);
        assertEquals(5, users.size());
        users = repository.findByRoles(Roles.ROLE_MOD);
        assertEquals(3, users.size());
        users = repository.findByRoles(Roles.ROLE_ADMIN);
        assertEquals(1, users.size());
    }
}