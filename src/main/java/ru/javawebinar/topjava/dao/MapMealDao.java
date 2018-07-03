package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MapMealDao implements MealDao {
    private static final Logger LOG = getLogger(MapMealDao.class);
    private final Map<Integer, Meal> mapStorage = new ConcurrentHashMap<>();
    private final AtomicInteger integer = new AtomicInteger(-1);

    @Override
    public Meal save(Meal meal) {
        Objects.requireNonNull(meal, "Bad news, we received null for save!");
        meal.setId(integer.incrementAndGet());
        LOG.info("Save " + meal);
        mapStorage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(Integer id) {
        LOG.info("Get " + id);
        return mapStorage.get(id);
    }

    @Override
    public void update(Meal meal) {
        LOG.info("Update " + meal);
        Objects.requireNonNull(meal, "Bad news, we received null for update!");
        mapStorage.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        LOG.info("Delete " + id);
        mapStorage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        LOG.info("getAll");
        return new ArrayList<>(mapStorage.values());
    }
}
