package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.profile.resolver.JdbcProfileResolver;

@ActiveProfiles(resolver = JdbcProfileResolver.class)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
