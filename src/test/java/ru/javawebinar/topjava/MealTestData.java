package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal USER_MEAL_1 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0), "Еда на граничное значение", 100);
    public static final Meal ADMIN_MEAL_5 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal ADMIN_MEAL_6 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal ADMIN_MEAL_7 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal NOT_EXIST = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal USER_MEAL_FOR_UPDATE = new Meal(100005, LocalDateTime.of(2020, Month.FEBRUARY, 25, 0, 0), "Dinner", 700);
    public static final LocalDate TEST_DATE = LocalDate.of(2020, Month.JANUARY, 31);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 23, 20, 0), "Dinner", 500);
    }

    public static Meal getUpdatedMeal() {
        Meal meal = new Meal(USER_MEAL_4);
        meal.setDateTime(LocalDateTime.of(2020, Month.FEBRUARY, 25, 0, 0));
        meal.setCalories(700);
        meal.setDescription("Dinner");
        return meal;
    }

    public static Meal getNotExistingMeal() {
        Meal meal = new Meal(ADMIN_MEAL_7);
        meal.setId(100);
        meal.setCalories(700);
        meal.setDescription("Dinner1");
        return meal;
    }

    public static Meal getMealAlien() {
        Meal meal = new Meal(ADMIN_MEAL_7);
        meal.setId(SecurityUtil.authUserId());
        meal.setCalories(700);
        meal.setDescription("Dinner1");
        return meal;
    }
}
