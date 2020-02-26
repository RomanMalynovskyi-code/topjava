package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(NOT_EXIST.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAlienMeal() {
        mealService.get(ADMIN_MEAL_5.getId(), USER_ID);
    }

    @Test
    public void get() {
        Meal meal = mealService.get(USER_MEAL_1.getId(), USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlienMeal() {
        mealService.delete(ADMIN_MEAL_6.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(NOT_EXIST.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        mealService.delete(USER_MEAL_2.getId(), USER_ID);
        mealService.get(USER_MEAL_2.getId(), USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> mealList = mealService.getBetweenHalfOpen(TEST_DATE, TEST_DATE, ADMIN_ID);
        assertMatch(mealList, ADMIN_MEAL_7, ADMIN_MEAL_6, ADMIN_MEAL_5);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = mealService.getAll(USER_ID);
        assertMatch(mealList, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1, USER_MEAL_4);
    }

    @Test
    public void update() {
        Meal meal = getUpdatedMeal();
        mealService.update(meal, USER_ID);
        assertMatch(meal, USER_MEAL_FOR_UPDATE);
    }


    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = getNotExistingMeal();
        mealService.update(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlienMeal() {
        Meal meal = getMealAlien();
        mealService.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getNewMeal();
        Meal created = mealService.create(newMeal, USER_ID);
        assertMatch(created, newMeal);
    }
}