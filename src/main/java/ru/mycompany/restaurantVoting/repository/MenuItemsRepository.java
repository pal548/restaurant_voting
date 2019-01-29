package ru.mycompany.restaurantVoting.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mycompany.restaurantVoting.model.MenuItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuItemsRepository extends CrudRepository<MenuItem, Integer> {
    List<MenuItem> findAllByRestaurantIdAndDay(Integer rest_id, LocalDate day);

    Optional<MenuItem> findByIdAndRestaurantIdAndDay(Integer id, Integer rest_id, LocalDate day);

    int deleteByIdAndRestaurantIdAndDay(Integer id, Integer rest_id, LocalDate day);
}
