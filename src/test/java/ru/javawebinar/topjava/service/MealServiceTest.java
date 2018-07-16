package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2018, 07, 14, 9, 36), "Завтрак", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatchMeal(service.getAll(USER_ID), MEAL_3, MEAL_2, MEAL_1, newMeal);
    }

    @Test
    public void delete() {
        service.delete(4, ADMIN_ID);
        assertMatchMeal(service.getAll(ADMIN_ID), MEAL_6, MEAL_5);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1, ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(1, USER_ID);
        assertMatchMeal(meal, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(4, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2018, 07, 16),
                LocalDate.of(2018, 07, 17), USER_ID);
        assertMatchMeal(all, MEAL_3, MEAL_2);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> all = service.getBetweenDateTimes(LocalDateTime.of(2018, 07, 15, 9, 0),
                LocalDateTime.of(2018, 07, 16, 12, 40), USER_ID);
        assertMatchMeal(all, MEAL_2, MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("TestОбед");
        updated.setCalories(16);
        service.update(updated, USER_ID);
        assertMatchMeal(service.get(1, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(MEAL_5, USER_ID);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatchMeal(all, MEAL_3, MEAL_2, MEAL_1);
    }


}