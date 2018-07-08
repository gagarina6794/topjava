package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpringMain {
    public static void main(String[] args) {
        //   java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 22, 15), "Обед8", 502));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 22, 59), "Завтрак", 402));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 22, 0), "Обед3", 2504));

            System.out.println("-------null String--------");
            mealRestController.filterString("ед").forEach(System.out::println);
            System.out.println("-------null String--------");

            System.out.println("-------null Time--------");
            mealRestController.filterTime(LocalDate.of(2015, Month.MAY, 29), LocalDate.of(2015, Month.MAY, 31),
               LocalTime.of(7, 5), LocalTime.of(22, 59)).forEach(System.out::println);
            System.out.println("-------null Time--------");

        }
    }
}
