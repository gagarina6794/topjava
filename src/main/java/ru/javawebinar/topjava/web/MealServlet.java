package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DataMeal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealDao storageMeals = DataMeal.getStorageMeals();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("doPost meals serlvlet");
        String idGet = request.getParameter("id");

        String date = request.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        String description = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));

        if (!idGet.equals("-1")) {
            storageMeals.update(new Meal(Integer.valueOf(idGet), dateTime, description, calories));
        } else {
            storageMeals.save(new Meal(dateTime, description, calories));
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(storageMeals.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                if (!id.equals("-1")) {
                    storageMeals.delete(Integer.valueOf(id));
                }
                response.sendRedirect("meals");
                return;
            case "edit":
                if (id.equals("-1")) {
                    meal = new Meal();
                    meal.setId(-1);
                } else {
                    meal = storageMeals.get(Integer.valueOf(id));
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
}
