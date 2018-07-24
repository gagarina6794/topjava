package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface CriteriaHelper {
    CriteriaQuery<Meal> query(CriteriaBuilder builder, CriteriaQuery<Meal> query, Root<Meal> root);
}
