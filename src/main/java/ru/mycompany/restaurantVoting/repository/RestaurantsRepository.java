package ru.mycompany.restaurantVoting.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mycompany.restaurantVoting.model.Restaurant;

public interface RestaurantsRepository extends CrudRepository<Restaurant, Integer> {

}
