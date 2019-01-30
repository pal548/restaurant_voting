package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mycompany.restaurantVoting.model.User;
import ru.mycompany.restaurantVoting.repository.UsersRepository;
import ru.mycompany.restaurantVoting.web.rest.util.RestUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static ru.mycompany.restaurantVoting.web.rest.util.RestUtil.assureIdConsistent;
import static ru.mycompany.restaurantVoting.web.rest.util.RestUtil.getResponseEntityNoContentOrNotFound;

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
        return RestUtil.getResponseEntityFromOptional(optionalUser);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User created = repo.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid  @RequestBody User user, @PathVariable("id") int id) {
        assureIdConsistent(user, id);
        repo.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        repo.deleteById(id);
    }


}
