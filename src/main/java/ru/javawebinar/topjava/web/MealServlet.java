package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MapMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static AtomicInteger integer = new AtomicInteger(-1);
    private static final Dao storageMeals = new MapMealDao();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        Meal meal;

        if (id.equals(-1)) {
            meal = new Meal(integer.incrementAndGet());
        } else {
            meal = storageMeals.get(id);
        }

        String description = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));
        String date = request.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        meal.setCalories(calories);
        meal.setDescription(description);
        meal.setDateTime(dateTime);

        if (id.equals(-1)) {
            storageMeals.save(meal);
        } else {
            storageMeals.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> MEAL_WITH_EXCEEDS = MealsUtil.getFilteredWithExceeded(storageMeals.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        String id = request.getParameter("id");
        Integer id1;
        if (id != null) {
            id1 = Integer.valueOf(id);
        } else {
            id1 = 0;
        }
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MEAL_WITH_EXCEEDS);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                storageMeals.delete(id1);
                response.sendRedirect("meals");
                return;
            case "edit":
                meal = storageMeals.get(id1);
                break;
            case "new":
                meal = new Meal();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(
                ("new".equals(action) ? "new.jsp" : "edit.jsp")
        ).forward(request, response);
    }
}
