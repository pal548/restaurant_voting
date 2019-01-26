package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mycompany.restaurantVoting.service.VotesServiceImpl;
import ru.mycompany.restaurantVoting.web.SecurityUtil;


@RestController
@RequestMapping(value = VotesController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotesController {
    static final String URL = "rest/vote/restaurant";

    private final VotesServiceImpl votesService;

    @Autowired
    public VotesController(VotesServiceImpl votesService) {
        this.votesService = votesService;
    }

    @PutMapping("/{restId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable("restId") int restId) {
        votesService.putVote(SecurityUtil.getAuthUserId(), restId);
    }
}
