package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        //   java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            SecurityUtil.setAuthUserId(1);
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 22, 15), "Обед8", 502));

            SecurityUtil.setAuthUserId(2);
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 22, 59), "Завтрак", 402));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 22, 0), "Обед3", 2504));

            System.out.println("Id user = " + SecurityUtil.authUserId());


            SecurityUtil.setAuthUserId(1);

            //Meal meal = new Meal(9, LocalDateTime.of(2018, Month.MAY, 31, 22, 0), "newMeal", 1331);
            // mealRestController.update(meal,9);
            System.out.println("-------null Time--------");
            mealRestController.filterTime(LocalDate.of(2015, Month.MAY, 31), LocalDate.of(2015, Month.MAY, 31),
                    LocalTime.of(20, 00), LocalTime.of(22, 59)).forEach(System.out::println);
            System.out.println("-------null Time--------");
            mealRestController.getAll().forEach(System.out::println);

        }
    }
}
