package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
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
        Map<LocalDate, List<UserMeal>> map = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        for (UserMeal u : mealList) {
            if (!map.containsKey(u.getDateTime().toLocalDate())) {
                map.put(u.getDateTime().toLocalDate(),
                        new ArrayList<>(Arrays.asList(u)));
            } else {
                List<UserMeal> listUser = map.get(u.getDateTime().toLocalDate());
                listUser.add(u);
                map.put(u.getDateTime().toLocalDate(), listUser);
            }
            if (mealList.get(mealList.size() - 1).equals(u)) {
                map.values().forEach(v -> {
                    if (v.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay) {
                        v.forEach(c -> userMealWithExceeds.add(new UserMealWithExceed(c.getDateTime(), c.getDescription(), c.getCalories(), true)));
                    } else {
                        v.forEach(c -> userMealWithExceeds.add(new UserMealWithExceed(c.getDateTime(), c.getDescription(), c.getCalories(), false)));
                    }
                });
            }
        }
        return userMealWithExceeds.stream().filter(t -> TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());
    }
}
