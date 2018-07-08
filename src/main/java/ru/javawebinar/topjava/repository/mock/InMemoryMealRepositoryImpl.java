package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.DTO;

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
    private int userId = SecurityUtil.authUserId();

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal != null) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId() + "_" + userId, meal);
                return meal;
            }
            return repository.computeIfPresent(meal.getId() + "_" + userId, (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        return (repository.remove(id + "_" + userId) != null);
    }

    @Override
    public Meal get(int id) {
        Meal meal = repository.get(id + "_" + userId);
        return (meal != null && meal.getId() == id ? meal : null);
    }

    @Override
    public List<MealWithExceed> getAll() {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<String, Meal> mealEntry : repository.entrySet()) {
            if (mealEntry.getKey().endsWith("_" + userId)) {
                meals.add(mealEntry.getValue());
            }
        }
        meals.sort(Comparator.comparing(Meal::getDate).reversed());
        return DTO.getWithExceeded(meals,MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public List<MealWithExceed> filterTime(LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd) {
        if(!getAll().isEmpty()) {
            return getAll().stream()
                    .filter(meal -> DateTimeUtil.isBetweenDateTime(meal, dateBegin, dateEnd, timeBegin, timeEnd))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<MealWithExceed> filterString(String str) {
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

