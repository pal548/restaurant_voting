package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mycompany.restaurantVoting.model.MenuItem;
import ru.mycompany.restaurantVoting.repository.MenuItemsRepository;

import java.util.List;

@RestController
@RequestMapping(value = MenuItemsRestController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemsRestController {
    static final String URL = "rest/admin/menu-items/restaurant";

    private final MenuItemsRepository repo;

    @Autowired
    public MenuItemsRestController(MenuItemsRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{rest_id}")
    public List<MenuItem> getAllByRestaurant(@PathVariable("rest_id") Integer rest_id) {
        return repo.findAllByRestaurantId(rest_id);
    }
}
