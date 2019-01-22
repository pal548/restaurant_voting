package ru.mycompany.restaurantVoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mycompany.restaurantVoting.model.Restaurant;

public interface RestaurantsRepository extends JpaRepository<Restaurant, Integer> {

}