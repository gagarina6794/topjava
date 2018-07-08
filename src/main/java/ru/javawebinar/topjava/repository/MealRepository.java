package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public interface MealRepository {

    Meal save(Meal meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    List<MealWithExceed> getAll();

    public List<MealWithExceed> filterTime(LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd);

    public List<MealWithExceed> filterString(String str);
}
