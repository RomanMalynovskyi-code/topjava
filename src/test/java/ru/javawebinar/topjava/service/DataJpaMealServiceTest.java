package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Autowired
    private MealService mealService;

    @Test
    public void getWithUser() throws Exception {
        Meal actual = mealService.getWithUser(USER_ID, MEAL1_ID);
        MEAL_MATCHER.assertMatch(actual, MEAL1);
        USER_MATCHER.assertMatch(actual.getUser(), USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundWithUser() throws Exception {
        mealService.getWithUser(ADMIN_ID, MEAL1_ID);
    }
}
