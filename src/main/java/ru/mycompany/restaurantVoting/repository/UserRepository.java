package ru.mycompany.restaurantVoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.mycompany.restaurantVoting.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u")
    List<User> findAllWithRoles();
}
