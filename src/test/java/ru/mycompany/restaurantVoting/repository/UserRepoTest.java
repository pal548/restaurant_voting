package ru.mycompany.restaurantVoting.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:spring/spring-main.xml")
//@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserRepoTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testUsersGetAll() {
        usersRepository.findAllWithRoles();
    }
}
