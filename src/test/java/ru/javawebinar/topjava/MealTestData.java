package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final Meal USER_MEAL_1 = new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0), "Еда на граничное значение", 100);
    public static final Meal ADMIN_MEAL_5 = new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal ADMIN_MEAL_6 = new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal ADMIN_MEAL_7 = new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal NOT_EXIST = new Meal(8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal USER_MEAL_FOR_UPDATE = new Meal(4, LocalDateTime.of(2020, Month.FEBRUARY, 25, 0, 0), "Dinner", 700);

    public static boolean assertMatch(Meal actual, Meal expected) {
        return actual.getId().equals(expected.getId()) && actual.getDateTime().equals(expected.getDateTime()) &&
                actual.getDescription().equals(expected.getDescription()) && actual.getCalories() == expected.getCalories();
    }
}
