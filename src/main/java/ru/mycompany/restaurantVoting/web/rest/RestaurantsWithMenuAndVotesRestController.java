package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mycompany.restaurantVoting.repository.RestaurantsWithMenuAndVotesRepository;
import ru.mycompany.restaurantVoting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantsWithMenuAndVotesRestController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsWithMenuAndVotesRestController {
    public static final String URL = "/rest/today-restaurants";

    private final RestaurantsWithMenuAndVotesRepository repository;

    @Autowired
    public RestaurantsWithMenuAndVotesRestController(RestaurantsWithMenuAndVotesRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        return repository.getAll(LocalDate.now());
    }

}
