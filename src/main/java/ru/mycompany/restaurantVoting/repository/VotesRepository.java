package ru.mycompany.restaurantVoting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;

@Repository
public class VotesRepository {

    private final SimpleJdbcInsert insertVote;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VotesRepository(JdbcTemplate jdbcTemplate) {
        this.insertVote = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("VOTES");
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insert(int userId, int restaurantId, LocalDate day) {
        return insertVote.execute(new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("restaurant_id", restaurantId)
                .addValue("day", day)) == 1;
    }

    public boolean exists(int userId, LocalDate day) {
        Boolean exists = jdbcTemplate.query("SELECT RESTAURANT_ID FROM VOTES " +
                        " WHERE USER_ID = ? " +
                        "       AND DAY = ? ",
                ResultSet::next,
                userId, day);
        return exists != null && exists;
    }

    public void update(int userId, int restaurantId, LocalDate day) {
        jdbcTemplate.update("UPDATE VOTES " +
                "SET RESTAURANT_ID = ? " +
                "WHERE USER_ID = ? AND DAY = ? ", restaurantId, userId, day);
    }
}
