package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public List<MealTo> getAllBetweenDates(LocalDateTime start, LocalDateTime end) {
        List<Meal> mealList = service.getBetweenDates(start.toLocalDate(), end.toLocalDate(), SecurityUtil.authUserId());
        return MealsUtil.getTos(mealList, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllBetweenTimes(LocalDateTime start, LocalDateTime end) {
        List<Meal> mealList = service.getAll(SecurityUtil.authUserId());
        List<Meal> resultList = new ArrayList<>();
        if (start != null && end != null) {
            resultList = getFilteredList(mealList, start.toLocalTime(), end.toLocalTime());
        } else if (start != null) {
            resultList = getFilteredList(mealList, start.toLocalTime(), LocalTime.MAX);
        } else if (end != null) {
            resultList = getFilteredList(mealList, LocalTime.MIN, end.toLocalTime());
        }
        return MealsUtil.getTos(resultList, SecurityUtil.authUserCaloriesPerDay());
    }

    private List<Meal> getFilteredList(List<Meal> mealList, LocalTime start, LocalTime end) {
        return mealList.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), start, end))
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, meal.getUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}