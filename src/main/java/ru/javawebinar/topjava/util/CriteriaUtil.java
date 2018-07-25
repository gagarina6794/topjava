package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class CriteriaUtil {

    public List<Meal> getList(EntityManager em, int userId, int id, LocalDateTime startDate, LocalDateTime endDate, boolean filter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Meal> query = builder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        CriteriaQuery<Meal> select = query.select(root);
        CriteriaQuery<Meal> dateTime;
        Predicate equal = builder.equal(root.get("user").get("id"), userId);

        if (id != 0) {
            select.where(builder.equal(root.get("id"), id), equal);
        } else {
            if (filter) {
                dateTime = select.where(builder.between(root.get("dateTime"), startDate, endDate), equal);
            } else {
                dateTime = select.where(equal);
            }
            dateTime.orderBy(builder.desc(root.get("dateTime")));
        }

        TypedQuery<Meal> queryResult = em.createQuery(query);
        return queryResult.getResultList();
    }
}
