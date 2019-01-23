package ru.mycompany.restaurantVoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycompany.restaurantVoting.model.MenuItem;
import ru.mycompany.restaurantVoting.repository.MenuItemsRepository;
import ru.mycompany.restaurantVoting.repository.RestaurantsRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MenuItemsServiceImpl {

    private final MenuItemsRepository menuItemsRepository;

    private final RestaurantsRepository restaurantsRepository;

    @Autowired
    public MenuItemsServiceImpl(MenuItemsRepository menuItemsRepository, RestaurantsRepository restaurantsRepository) {
        this.menuItemsRepository = menuItemsRepository;
        this.restaurantsRepository = restaurantsRepository;
    }

    @Transactional
    public MenuItem save(MenuItem menuItem, LocalDate day, Integer rest_id) {
        if (!menuItem.isNew()) {
            Optional<MenuItem> old = menuItemsRepository.findByIdAndRestaurantIdAndDay(menuItem.getId(), rest_id, day);
            if (!old.isPresent()) {
                return null;
            }
        }
        menuItem.setRestaurant(restaurantsRepository.getOne(rest_id));
        menuItem.setDay(day);
        return menuItemsRepository.save(menuItem);
    }
}
