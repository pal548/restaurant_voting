package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mycompany.restaurantVoting.model.User;
import ru.mycompany.restaurantVoting.repository.UsersRepository;
import ru.mycompany.restaurantVoting.web.rest.util.RestUtil;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = UsersRestController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersRestController {
    public static final String URL = "rest/admin/users";

    private UsersRepository repo;

    @Autowired
    public UsersRestController(UsersRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> getAll() {
        return repo.findAllWithRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Integer id) {
        Optional<User> optionalUser = repo.findById(id);
        return RestUtil.getResposeEntityFromOptional(optionalUser);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = repo.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}
