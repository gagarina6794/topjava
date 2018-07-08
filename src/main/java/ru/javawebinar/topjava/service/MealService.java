package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal create(Meal meal) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    void update(Meal meal);

    List<MealWithExceed> getAll();

    public List<MealWithExceed> filterTime(LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd);

    public List<MealWithExceed> filterString(String str);
}