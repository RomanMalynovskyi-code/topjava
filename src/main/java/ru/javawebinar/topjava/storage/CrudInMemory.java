package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface CrudInMemory {

    void addMeal(Meal meal);

    void updateMeal(Meal meal);

    void deleteMeal(Long id);

    Meal getMealById(Long id);

    List<Meal> getMealList();
}
