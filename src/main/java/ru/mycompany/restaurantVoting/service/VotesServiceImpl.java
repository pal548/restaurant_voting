package ru.mycompany.restaurantVoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycompany.restaurantVoting.exceptions.VoteAfterDeadlineException;
import ru.mycompany.restaurantVoting.repository.VotesRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional(readOnly = true)
public class VotesServiceImpl {

    private final VotesRepository repository;

    @Value("#{ T(java.time.LocalTime).parse('${deadline.time}')}")
    private LocalTime deadlineTime;

    @Autowired
    public VotesServiceImpl(VotesRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void putVote(int userId, int restaurantId) {
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        boolean exists = repository.exists(userId, today);
        if (exists) {
            if (time.isAfter(deadlineTime)) {
                throw new VoteAfterDeadlineException();
            }
            repository.update(userId, restaurantId, today);
        } else {
            repository.insert(userId, restaurantId, today);
        }
    }
}
