package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> map = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        mealList.forEach(u -> map.merge(u.getDateTime().toLocalDate(), new ArrayList<>(Collections.singletonList(u)), (x, y) -> x.addAll(y) ? x : y));

        map.values().forEach(v -> {
            if (v.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay) {
                convertList(startTime, endTime, userMealWithExceeds, v, true);
            } else {
                convertList(startTime, endTime, userMealWithExceeds, v, false);
            }
        });
        return userMealWithExceeds;
    }

    private static void convertList(LocalTime startTime, LocalTime endTime, List<UserMealWithExceed> userMealWithExceeds, List<UserMeal> v, boolean b) {
        v.stream().filter(t -> TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(c -> userMealWithExceeds.add(new UserMealWithExceed(c.getDateTime(), c.getDescription(), c.getCalories(), b)));
    }
}
