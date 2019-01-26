package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mycompany.restaurantVoting.model.MenuItem;
import ru.mycompany.restaurantVoting.repository.MenuItemsRepository;
import ru.mycompany.restaurantVoting.repository.RestaurantsRepository;
import ru.mycompany.restaurantVoting.service.MenuItemsServiceImpl;
import ru.mycompany.restaurantVoting.web.rest.util.RestUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        return RestUtil.getResposeEntityFromOptional(menuItemsRepository.findByIdAndRestaurantIdAndDay(id, rest_id, LocalDate.now()));
    }

    @PostMapping(value = "/{rest_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem menuItem, @PathVariable("rest_id") int rest_id) {
        // !!! можно напороться конкретно, если не сбросить id в null... - тогда можно через вызов create() сделать обновление существующей сущности, подставив нужный id
        // можно сделать через 2 разных метода в сервисе...
        // в топджава решается как раз так, проверкой checkNew(_entity_) в методе контроллера create()
        menuItem.setId(null);
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
    public ResponseEntity<Void> update(@RequestBody MenuItem menuItem, @PathVariable("rest_id") int rest_id, @PathVariable("id") int id) {
        if (!menuItemsService.save(menuItem, LocalDate.now(), rest_id).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
