package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface CrudOfMeal {

    void add(Meal meal);

    void update(Meal meal);

    void delete(Long id);

    Meal getById(Long id);

    List<Meal> getList();
}
