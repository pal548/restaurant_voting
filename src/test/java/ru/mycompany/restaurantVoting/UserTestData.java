package ru.mycompany.restaurantVoting;

import ru.mycompany.restaurantVoting.model.Role;
import ru.mycompany.restaurantVoting.model.User;

import static ru.mycompany.restaurantVoting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "User Name", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "Admin Name", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
}
