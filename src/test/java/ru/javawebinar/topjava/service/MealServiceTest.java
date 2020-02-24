package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(10, 100000);
    }

    @Test(expected = NotFoundException.class)
    public void getAlienMeal() {
        mealService.get(5, 100000);
    }

    @Test
    public void get() {
        Meal meal = mealService.get(1, 100000);
        Assert.assertEquals(true, meal.equals(MEAL_1));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlienMeal() {
        mealService.delete(6, 10000);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(100, 10001);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> mealList = mealService.getBetweenHalfOpen(LocalDate.of(2020, Month.JANUARY, 31),
                LocalDate.of(2020, Month.JANUARY, 31), 100001);
        Assert.assertEquals(3, mealList.size());
        Assert.assertArrayEquals(new Meal[]{new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000)}, mealList.toArray());
    }

    @Test
    public void getAll() {
        List<Meal> mealList = mealService.getAll(100000);
        Assert.assertEquals(4, mealList.size());
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL_4);
        meal.setCalories(700);
        meal.setDescription("Dinner");
        mealService.update(meal, 100000);
        Assert.assertEquals(meal.getDateTime(), mealService.get(MEAL_4.getId(), 100000).getDateTime());
        Assert.assertEquals("Dinner", mealService.get(MEAL_4.getId(), 100000).getDescription());
        Assert.assertEquals(700, mealService.get(MEAL_4.getId(), 100000).getCalories());
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(MEAL_7);
        meal.setId(100);
        meal.setCalories(700);
        meal.setDescription("Dinner1");
        mealService.update(meal, 100000);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlienMeal() {
        Meal meal = new Meal(MEAL_7);
        meal.setId(SecurityUtil.authUserId());
        meal.setCalories(700);
        meal.setDescription("Dinner1");
        mealService.update(meal, 100001);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 23, 20, 0), "Dinner", 500);
        Meal created = mealService.create(newMeal, 100000);
        assertThat(created).isEqualToComparingFieldByField(newMeal);
    }
}