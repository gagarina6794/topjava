package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController mealRestController;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private LocalTime timeBegin;
    private LocalTime timeEnd;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRestController = appCtx.getBean(MealRestController.class);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String dateBeginR = request.getParameter("datebegin");
        String dateEndR = request.getParameter("dateend");
        String timeBeginR = request.getParameter("timebegin");
        String timeEndR = request.getParameter("timeend");

        if (!id.equals("null")) {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.create(meal);
        }

        if (id.equals("null")) {
            if (!dateBeginR.isEmpty()) {
                dateBegin = LocalDate.parse(dateBeginR);
            } else {
                dateBegin = null;
            }
            if (!dateEndR.isEmpty()) {
                dateEnd = LocalDate.parse(dateEndR);
            } else {
                dateEnd = null;
            }
            if (!timeBeginR.isEmpty()) {
                timeBegin = LocalTime.parse(timeBeginR);
            } else {
                timeBegin = null;
            }
            if (!timeEndR.isEmpty()) {
                timeEnd = LocalTime.parse(timeEndR);
            } else {
                timeEnd = null;
            }
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "cancel":
                log.info("All");
                request.setAttribute("meals",
                        mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAllFiltered");
                request.setAttribute("meals",
                        mealRestController.filterTime(dateBegin, dateEnd, timeBegin, timeEnd));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        log.info("MealServlet destroy");
        appCtx.close();
        super.destroy();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
