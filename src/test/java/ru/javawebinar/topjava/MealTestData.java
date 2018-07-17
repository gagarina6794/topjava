package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int USER_MEAL_ID_2 = START_SEQ + 3;
    public static final int USER_MEAL_ID_3 = START_SEQ + 4;
    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 5;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 6;
    public static final int ADMIN_MEAL_ID_3 = START_SEQ + 7;

    public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID_1,
            LocalDateTime.of(2018, 7, 15, 9, 36), "Завтрак Пользователя", 500);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID_2,
            LocalDateTime.of(2018, 7, 16, 12, 36), "Обед Пользователя", 1000);
    public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID_3,
            LocalDateTime.of(2018, 7, 17, 17, 36), "Ужин Пользователя", 300);
    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1,
            LocalDateTime.of(2018, 7, 17, 9, 36), "Завтрак Админа", 150);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID_2,
            LocalDateTime.of(2018, 7, 17, 12, 36), "Обед Админа", 200);
    public static final Meal ADMIN_MEAL_3 = new Meal(ADMIN_MEAL_ID_3,
            LocalDateTime.of(2018, 7, 17, 17, 36), "Ужин Админа", 130);

    public static void assertMatchMeal(Meal actual, Meal expected) {
        if (!actual.getId().equals(expected.getId())) {
            throw new NotFoundException("Not found meal with id " + expected.getId());
        }
    }

    public static void assertMatchMeal(List<Meal> actual, Meal... expected) {
        List<Integer> actuald = actual.stream().map(Meal::getId).collect(Collectors.toList());
        List<Integer> expectedld = Arrays.stream(expected).map(Meal::getId).collect(Collectors.toList());
        if (!actuald.equals(expectedld)) {
            throw new RuntimeException("List's are not equals");
        }
    }
}
