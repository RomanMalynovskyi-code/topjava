package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.storage.StorageImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static Storage storage = new StorageImpl();
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String EDIT = "/updateMeal.jsp";
    private static final String ADD = "/addMeal.jsp";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");
        String forward;
        String action = req.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("edit")) {
                forward = EDIT;
                req.setAttribute("formatter", dateTimeFormatter);
                Meal mealById = storage.getMealById(Long.parseLong(req.getParameter("id")));
                req.setAttribute("meal", mealById);
                req.setAttribute("formatter", dateTimeFormatter);
                showMealList(req, resp, forward);
            } else if (action.equalsIgnoreCase("insert")) {
                forward = ADD;
                showMealList(req, resp, forward);
            } else if (action.equalsIgnoreCase("delete")) {
                doDelete(req, resp);
            }
        } else {
            forward = LIST_MEAL;
            showMealList(req, resp, forward);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), dateTimeFormatter);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String id = req.getParameter("id");
        if (id == null) {
            log.debug("forward to addMeal");
            storage.addMeal(meal);
        } else {
            log.debug("forward to updateMeal");
            meal.setId(Long.parseLong(req.getParameter("id")));
            meal.setDescription(description);
            meal.setDateTime(dateTime);
            meal.setCalories(calories);
            storage.updateMeal(meal);
        }
        showMealList(req, resp, LIST_MEAL);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        storage.deleteMeal(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("meals");
    }

    private void showMealList(HttpServletRequest req, HttpServletResponse resp, String forward) throws ServletException, IOException {
        List<Meal> mealList = storage.getMealList();
        List<MealTo> mealToList = MealsUtil.filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealToList", mealToList);
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }
}
