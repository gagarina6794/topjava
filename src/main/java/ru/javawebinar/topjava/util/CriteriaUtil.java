package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaUtil {

    public List<Meal> getList(EntityManager em, CriteriaHelper criteriaHelper) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Meal> query = builder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query = criteriaHelper.query(builder, query, root);
        return em.createQuery(query).getResultList();
    }
}
