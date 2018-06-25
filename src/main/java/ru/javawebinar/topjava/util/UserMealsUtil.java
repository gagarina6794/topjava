package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        mealList.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()))
                .entrySet()
                .iterator()
                .forEachRemaining(UserMealMap -> convertMap(startTime, endTime, userMealWithExceeds, UserMealMap.getValue(), UserMealMap.getValue().stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay));

        return userMealWithExceeds;
    }

    private static void convertMap(LocalTime startTime, LocalTime endTime, List<UserMealWithExceed> userMealWithExceeds, List<UserMeal> mealList, boolean b) {
        mealList.stream().filter(time -> TimeUtil.isBetween(time.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(userMeal -> userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), b)));
    }
}
