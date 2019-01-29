package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mycompany.restaurantVoting.View;
import ru.mycompany.restaurantVoting.model.MenuItem;
import ru.mycompany.restaurantVoting.repository.MenuItemsRepository;
import ru.mycompany.restaurantVoting.repository.RestaurantsRepository;
import ru.mycompany.restaurantVoting.service.MenuItemsServiceImpl;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.mycompany.restaurantVoting.web.rest.util.RestUtil.*;

@RestController
@RequestMapping(value = MenuItemsRestController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemsRestController {
    static final String URL = "rest/admin/menu-items/restaurant";

    private final MenuItemsRepository menuItemsRepository;

    private final MenuItemsServiceImpl menuItemsService;

    @Autowired
    public MenuItemsRestController(MenuItemsRepository menuItemsRepository, RestaurantsRepository restaurantsRepository, MenuItemsServiceImpl menuItemsService) {
        this.menuItemsRepository = menuItemsRepository;
        this.menuItemsService = menuItemsService;
    }

    @GetMapping("/{rest_id}")
    public List<MenuItem> getAllByRestaurant(@PathVariable("rest_id") Integer rest_id) {
        return menuItemsRepository.findAllByRestaurantIdAndDay(rest_id, LocalDate.now());
    }

    @GetMapping("/{rest_id}/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable("rest_id") int rest_id, @PathVariable("id") int id) {
        return getResponseEntityFromOptional(menuItemsRepository.findByIdAndRestaurantIdAndDay(id, rest_id, LocalDate.now()));
    }

    @PostMapping(value = "/{rest_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@Validated(View.Rest.class) @RequestBody MenuItem menuItem, @PathVariable("rest_id") int rest_id) {
        checkNew(menuItem);
        MenuItem created =
                menuItemsService.save(menuItem, LocalDate.now(), rest_id)
                        .orElseThrow(() -> new IllegalStateException("Сохранение MenuItem вернуло null"));

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{rest_id}/{id}")
                .buildAndExpand(rest_id, created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{rest_id}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@Valid @RequestBody MenuItem menuItem, @PathVariable("rest_id") int rest_id, @PathVariable("id") int id) {
        assureIdConsistent(menuItem, id);
        return getResponseEntityNoContentOrNotFound(menuItemsService.save(menuItem, LocalDate.now(), rest_id).isPresent());
    }

    @DeleteMapping("/{rest_id}/{id}")
    public ResponseEntity<Void> delete(@PathVariable("rest_id") int rest_id, @PathVariable("id") int id) {
        return getResponseEntityNoContentOrNotFound(
                 menuItemsRepository.deleteByIdAndRestaurantIdAndDay(id, rest_id, LocalDate.now()) == 1);
    }

}
