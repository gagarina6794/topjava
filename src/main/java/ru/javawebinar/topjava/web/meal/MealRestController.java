package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
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

    public List<MealWithExceed> filterTime(LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd) {
        log.info("filterTime");

        List<MealWithExceed> mealWithExceeds =  service.getAll(SecurityUtil.authUserId());

        if (dateBegin == null) {
            dateBegin = mealWithExceeds.stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalDate())
                    .min(LocalDate::compareTo).orElse(LocalDateTime.MIN.toLocalDate());
        }
        if (timeBegin == null) {
            timeBegin = mealWithExceeds.stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalTime())
                    .min(LocalTime::compareTo).orElse(LocalDateTime.MIN.toLocalTime());
        }
        if (dateEnd == null) {
            dateEnd = mealWithExceeds.stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalDate())
                    .max(LocalDate::compareTo).orElse(LocalDateTime.MAX.toLocalDate());
        }
        if (timeEnd == null) {
            timeEnd = mealWithExceeds.stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalTime())
                    .max(LocalTime::compareTo).orElse(LocalDateTime.MAX.toLocalTime());
        }
        LocalDate beginDate = dateBegin;
        LocalDate endDate = dateEnd;
        LocalTime beginTime = timeBegin;
        LocalTime endTime = timeEnd;

        if (!getAll().isEmpty()) {
            return service.filterDate(SecurityUtil.authUserId(), dateBegin, dateEnd).stream()
                    .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), beginTime, endTime))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}