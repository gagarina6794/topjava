package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<String, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal != null) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId() + "_" + SecurityUtil.authUserId(), meal);
                return meal;
            }
            return repository.computeIfPresent(meal.getId() + "_" + SecurityUtil.authUserId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        return (repository.remove(id + "_" + SecurityUtil.authUserId()) != null);
    }

    @Override
    public Meal get(int id) {
        Meal meal = repository.get(id + "_" + SecurityUtil.authUserId());
        if (meal != null) {
            return (meal.getId() == id ? meal : null);
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<String, Meal> mealEntry : repository.entrySet()) {
            if (mealEntry.getKey().endsWith("_" + SecurityUtil.authUserId())) {
                meals.add(mealEntry.getValue());
            }
        }
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return meals;
    }

    @Override
    public List<Meal> filterTime(LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd) {
        if (!getAll().isEmpty()) {
            return getAll().stream()
                    .filter(meal -> DateTimeUtil.isBetweenDateTime(meal, dateBegin, dateEnd, timeBegin, timeEnd))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Meal> filterString(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (!getAll().isEmpty()) {
            return getAll().stream()
                    .filter(meal -> meal.getDateTime().format(formatter).contains(str)
                            || meal.getDescription().contains(str)
                            || String.valueOf(meal.getCalories()).contains(str))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}

