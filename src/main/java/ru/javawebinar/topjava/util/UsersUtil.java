package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "John", "john@gmail.com", "password", Role.ROLE_ADMIN, Role.values()),
            new User(2, "Marc", "marc@yahoo.com", "pass", Role.ROLE_USER, Role.values()),
            new User(3, "Kristoff", "kristoff@bk.ru", "kristoff", Role.ROLE_USER, Role.values()),
            new User(4, "Jonas", "jonas@gmail.com", "password1234", Role.ROLE_USER, Role.values()),
            new User(5, "Cristian", "cristian@gmail.com", "word", Role.ROLE_USER, Role.values()),
            new User(6, "Carlo", "carlo@yahoo.com", "1234567word", Role.ROLE_USER, Role.values()),
            new User(7, "Bobi", "bobi@outlook.com", "1234567890", Role.ROLE_USER, Role.values())
    );
}
