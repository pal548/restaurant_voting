package ru.mycompany.restaurantVoting.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mycompany.restaurantVoting.model.MenuItem;

import java.util.List;

public interface MenuItemsRepository extends CrudRepository<MenuItem, Integer> {
    List<MenuItem> findAllByRestaurantId(Integer rest_id);
}
