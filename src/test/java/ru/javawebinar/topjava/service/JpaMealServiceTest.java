package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.profile.resolver.JpaProfileResolver;

@ActiveProfiles(resolver = JpaProfileResolver.class)
public class JpaMealServiceTest extends AbstractMealServiceTest {
}
