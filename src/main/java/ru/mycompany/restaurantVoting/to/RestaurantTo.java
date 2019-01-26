package ru.mycompany.restaurantVoting.to;

import ru.mycompany.restaurantVoting.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTo {

    private final Integer id;

    private final String name;

    private final String address;

    private final int votesQty;

    private List<MenuItem> menuItems = new ArrayList<>();

    public RestaurantTo(Integer id, String name, String address, int votesQty) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.votesQty = votesQty;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public int getVotesQty() {
        return votesQty;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
