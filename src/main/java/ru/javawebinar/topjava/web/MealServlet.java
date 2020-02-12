package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.CrudOfMeal;
import ru.javawebinar.topjava.storage.CrudInMemory;
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
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String ADD_OR_EDIT = "/meal.jsp";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private CrudOfMeal crudOfMeal;

    @Override
    public void init() {
        crudOfMeal = new CrudInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("forward to meals");
        String forward;
        String action = Optional.ofNullable(req.getParameter("action")).orElse("showMealList");
        switch (action) {
            case "edit":
                forward = ADD_OR_EDIT;
                Meal mealById = crudOfMeal.getById(Long.parseLong(req.getParameter("id")));
                if (mealById != null) {
                    req.setAttribute("meal", mealById);
                    req.setAttribute("formatter", dateTimeFormatter);
                } else {
                    forward = LIST_MEAL;
                }
                break;
            case "insert":
                forward = ADD_OR_EDIT;
                break;
            case "delete":
                doDelete(req, resp);
                return;
            default:
                forward = LIST_MEAL;
                break;
        }
        showMealList(req, resp, forward);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meal");
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), dateTimeFormatter);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String id = req.getParameter("id");
        if (id == null || id.length() == 0) {
            crudOfMeal.add(meal);
        } else {
            meal = new Meal(Long.parseLong(id), dateTime, description, calories);
            crudOfMeal.update(meal);
        }
        showMealList(req, resp, LIST_MEAL);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        crudOfMeal.delete(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("meals");
    }

    private void showMealList(HttpServletRequest req, HttpServletResponse resp, String forward) throws ServletException, IOException {
        List<Meal> mealList = crudOfMeal.getList();
        List<MealTo> mealToList = MealsUtil.filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealToList", mealToList);
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }
}
