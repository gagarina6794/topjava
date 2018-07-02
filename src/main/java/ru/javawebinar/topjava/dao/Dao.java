package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    void save(Meal meal);

    Meal get(Integer id);

    List<Meal> getAll();

    void update(Meal meal);

    void delete(Integer id);
}
