package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class MapMealDao extends AbstractMealDao {

    private Map<Integer, Meal> mapStorage = new ConcurrentHashMap<>();

    @Override
    protected Integer getIndex(Integer id) {
        return id;
    }

    @Override
    protected boolean checkIndex(Integer index) {
        return mapStorage.containsKey(index);
    }

    @Override
    protected Stream<Meal> getStream() {
        return mapStorage.values().stream();
    }

    @Override
    protected void doSave(Meal meal, Integer index) {
        mapStorage.put(meal.getId(), meal);
    }

    @Override
    protected Meal doGet(Integer index) {
        return mapStorage.get(index);
    }

    @Override
    protected void doUpdate(Meal meal, Integer index) {
        mapStorage.put(meal.getId(), meal);
    }

    @Override
    protected void doDelete(Integer index) {
        mapStorage.remove(index);
    }
}