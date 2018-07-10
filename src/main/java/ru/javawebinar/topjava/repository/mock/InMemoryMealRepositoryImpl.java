package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal != null) {
            Map<Integer, Meal> map;
            if (repository.get(userId) != null) {
                map = new ConcurrentHashMap<>(repository.get(userId));
            } else {
                map = new ConcurrentHashMap<>();
            }
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                map.put(meal.getId(), meal);
                repository.put(userId, map);
                return meal;
            }
            Meal mealUpdate = map.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            repository.put(userId, map);
            return mealUpdate;
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        return (map.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> map;
        if (repository.get(userId) != null) {
            map = new ConcurrentHashMap<>(repository.get(userId));
        } else {
            map = new ConcurrentHashMap<>();
        }
        if (!map.isEmpty()) {
            return map.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Meal> filterDate(int userId, LocalDate dateBegin, LocalDate dateEnd) {
        if (!getAll(userId).isEmpty()) {
            return getAll(userId).stream()
                    .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), dateBegin, dateEnd))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}


