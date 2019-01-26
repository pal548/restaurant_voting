package ru.mycompany.restaurantVoting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mycompany.restaurantVoting.model.MenuItem;
import ru.mycompany.restaurantVoting.to.RestaurantTo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestaurantsWithMenuAndVotesRepository {

    private static String SQL =
            "WITH s1 AS (\n" +
                    "   SELECT v.RESTAURANT_ID,\n" +
                    "          count(*) AS votes_qty\n" +
                    "   FROM VOTES v\n" +
                    "   WHERE v.DAY = ?\n" +
                    "   GROUP BY v.RESTAURANT_ID\n" +
                    ")\n" +
                    "\n" +
                    "SELECT r.*,\n" +
                    "       mi.ID AS MENU_ID,\n" +
                    "       mi.NAME AS MENU_NAME,\n" +
                    "       mi.PRICE,\n" +
                    "       s1.VOTES_QTY\n" +
                    "FROM RESTAURANTS r\n" +
                    "     JOIN MENU_ITEMS mi ON mi.RESTAURANT_ID = r.ID\n" +
                    "     LEFT JOIN s1 ON s1.RESTAURANT_ID = r.ID\n" +
                    "WHERE mi.DAY = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RestaurantsWithMenuAndVotesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RestaurantTo> getAll(LocalDate day) {
        Timestamp today = Timestamp.valueOf(LocalDateTime.of(day, LocalTime.MIN));
        Map<Integer, RestaurantTo> map = new HashMap<>();
        jdbcTemplate.query(SQL,
                ps -> {
                    ps.setTimestamp(1, today);
                    ps.setTimestamp(2, today);
                },
                rs -> {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    int votesQty = rs.getInt("votes_qty");
                    RestaurantTo rest =
                            map.computeIfAbsent(id,
                                    restId -> new RestaurantTo(restId, name, address, votesQty));
                    rest.addMenuItem(
                            new MenuItem(
                                    rs.getInt("menu_id"),
                                    rs.getString("menu_name"),
                                    rs.getBigDecimal("price")));
                });
        return new ArrayList<>(map.values());
    }
}
