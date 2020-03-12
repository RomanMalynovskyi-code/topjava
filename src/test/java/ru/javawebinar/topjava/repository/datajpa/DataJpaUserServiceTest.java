package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getWithMeals() throws Exception {
        User user = userService.getWithMeals(USER_ID);
        MEAL_MATCHER.assertMatch(user.getMealList(), USER_MEALS);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundWithMeals() throws Exception {
        userService.getWithMeals(USER_ID + 3);
    }

    @Test
    public void getWithoutMeal() throws Exception {
        User newUser = getNew();
        User created = userService.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        MEAL_MATCHER.assertMatch(created.getMealList(), Collections.EMPTY_LIST);
    }
}
