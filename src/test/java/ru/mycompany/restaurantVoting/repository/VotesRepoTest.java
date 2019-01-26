package ru.mycompany.restaurantVoting.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.mycompany.restaurantVoting.UserTestData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(locations = "classpath:spring/spring-main.xml")
public class VotesRepoTest {

    @Autowired
    private VotesRepository repo;

    @Test
    void testInsertAndExists() {
        assertFalse(repo.exists(UserTestData.USER_ID, LocalDate.now()));
        assertTrue(repo.insert(UserTestData.USER_ID, 100003, LocalDate.now()));
        assertTrue(repo.exists(UserTestData.USER_ID, LocalDate.now()));
    }
}
