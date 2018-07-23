package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.TestJUnitStopWatch;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    final static Logger logger =
            LoggerFactory.getLogger(MealServiceTest.class);

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private static StringBuilder report = new StringBuilder();

    @Rule
    public TestRule watcher = new TestJUnitStopWatch() {
       @Override
        public void failed(long nanos, Throwable e, Description description) {
           report.append("  Fail: test ")
                   .append(description.getMethodName())
                   .append(" - ")
                   .append(TimeUnit.NANOSECONDS.toMicros(nanos)).append(" ms").append("\n");
           logger.warn(String.format("Test %s, spent %d microseconds",
                   description.getMethodName(),  TimeUnit.NANOSECONDS.toMicros(nanos)));
        }

        @Override
        public void succeeded(long nanos,Description description) {
            report.append("  Success: test ")
                    .append(description.getMethodName())
                    .append(" - ")
                    .append(TimeUnit.NANOSECONDS.toMicros(nanos)).append(" ms").append("\n");
            logger.info(String.format("Test %s, spent %d microseconds",
                    description.getMethodName(),  TimeUnit.NANOSECONDS.toMicros(nanos)));
        }
    };

    @AfterClass
    public static void tearDownClass() {
        System.out.println("@AfterClass report");
        logger.info("\n" + report.toString());
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void create() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        Meal actual = service.get(MEAL1_ID, USER_ID);
        assertMatch(actual, updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}