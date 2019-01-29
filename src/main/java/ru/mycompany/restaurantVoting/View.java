package ru.mycompany.restaurantVoting;

import javax.validation.groups.Default;

public class View {
    // Validate only from REST
    public interface Rest extends Default {}

    // Validate only on DB save/update
    public interface Persist extends Default {}
}
