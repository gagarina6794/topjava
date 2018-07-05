package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MapMealDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class DataMeal {

    private static final MealDao storageMeals = new MapMealDao();

    public static MealDao getStorageMeals() {
        storageMeals.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        storageMeals.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        storageMeals.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        storageMeals.save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        storageMeals.save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        storageMeals.save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
       return storageMeals;
    }
}
