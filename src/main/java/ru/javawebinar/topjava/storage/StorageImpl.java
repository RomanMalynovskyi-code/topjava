package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class StorageImpl implements Storage {
    private Map<Long, Meal> mealMap = new ConcurrentHashMap<>(MealsUtil.initMealMap());
    private AtomicLong id = new AtomicLong(mealMap.size());

    public Long getNextId() {
        return id.incrementAndGet();
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(getNextId());
        mealMap.put(id.get(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(Long id) {
        mealMap.remove(id);
    }

    @Override
    public Meal getMealById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getMealList() {
        return new ArrayList<>(mealMap.values());
    }
}
