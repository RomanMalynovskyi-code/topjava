package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CrudInMemory implements CrudOfMeal {
    private Map<Long, Meal> mealMap;
    private AtomicLong id;

    public CrudInMemory() {
        List<Meal> mealList = Arrays.asList(
                new Meal(1L, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(2L, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(3L, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(4L, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(5L, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(6L, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(7L, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        this.mealMap = new ConcurrentHashMap<>(mealList.stream()
                .collect(Collectors.toMap(Meal::getId, meal -> meal)));
        this.id = new AtomicLong(mealMap.size());
    }

    public Long getNextId() {
        return id.incrementAndGet();
    }

    @Override
    public void add(Meal meal) {
        meal.setId(getNextId());
        mealMap.put(id.get(), meal);
    }

    @Override
    public void update(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(Long id) {
        mealMap.remove(id);
    }

    @Override
    public Meal getById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(mealMap.values());
    }
}
