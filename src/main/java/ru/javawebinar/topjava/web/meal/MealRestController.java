package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {

    @Override
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        return super.create(meal);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(Meal meal, int id) {
        super.update(meal, id);
    }

    @Override
    public List<MealWithExceed> filterTime(LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd) {
        if (dateBegin == null) {
            dateBegin = super.getAll().stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalDate())
                    .min(LocalDate::compareTo).get();
        }
        if (timeBegin == null) {
            timeBegin = super.getAll().stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalTime())
                    .min(LocalTime::compareTo).get();
        }
        if (dateEnd == null) {
            dateEnd = super.getAll().stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalDate())
                    .max(LocalDate::compareTo).get();
        }
        if (timeEnd == null) {
            timeEnd = super.getAll().stream()
                    .map(mealWithExceed -> mealWithExceed.getDateTime().toLocalTime())
                    .max(LocalTime::compareTo).get();
        }
        return super.filterTime(dateBegin, dateEnd, timeBegin, timeEnd);
    }

    @Override
    public List<MealWithExceed> filterString(String str) {
        if (str == null) {
            str = "";
        }
        return super.filterString(str);
    }
}