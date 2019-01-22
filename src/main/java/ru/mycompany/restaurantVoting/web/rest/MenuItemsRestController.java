package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mycompany.restaurantVoting.model.MenuItem;
import ru.mycompany.restaurantVoting.repository.MenuItemsRepository;
import ru.mycompany.restaurantVoting.repository.RestaurantsRepository;
import ru.mycompany.restaurantVoting.web.rest.util.RestUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuItemsRestController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemsRestController {
    static final String URL = "rest/admin/menu-items/restaurant";

    private final MenuItemsRepository repo;

    private final RestaurantsRepository restaurantsRepo;

    @Autowired
    public MenuItemsRestController(MenuItemsRepository repo, RestaurantsRepository restaurantsRepo) {
        this.repo = repo;
        this.restaurantsRepo = restaurantsRepo;
    }

    @GetMapping("/{rest_id}")
    public List<MenuItem> getAllByRestaurant(@PathVariable("rest_id") Integer rest_id) {
        return repo.findAllByRestaurantId(rest_id);
    }

    @GetMapping("/{rest_id}/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable("rest_id") int rest_id, @PathVariable("id") int id) {
        return RestUtil.getResposeEntityFromOptional(repo.findByIdAndRestaurantIdAndDay(id, rest_id, LocalDate.now()));
    }

    @PostMapping(value = "/{rest_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem menuItem, @PathVariable("rest_id") int rest_id) {
        menuItem.setRestaurant(restaurantsRepo.getOne(rest_id));
        menuItem.setDay(LocalDate.now());
        MenuItem created = repo.save(menuItem);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{rest_id}/{id}")
                .buildAndExpand(rest_id, created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }
}
