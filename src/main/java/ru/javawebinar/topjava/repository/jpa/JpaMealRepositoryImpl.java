package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.CriteriaUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    private CriteriaUtil criteriaUtil = new CriteriaUtil();

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return get(meal.getId(), userId) != null ? em.merge(meal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> mealList = criteriaUtil.getList(em, (builder, query, root) -> query
                .select(root)
                .where(builder.equal(root.get("id"), id), builder.equal(root.get("user").get("id"), userId)));
        return DataAccessUtils.singleResult(mealList);

//        Meal meal = em.find(Meal.class, id);
//        return meal == null ? null : meal.getUser().getId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return criteriaUtil.getList(em, (builder, query, root) -> query
                .select(root)
                .where(builder.equal(root.get("user").get("id"), userId))
                .orderBy(builder.desc(root.get("dateTime"))));

//        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
//                .setParameter("user_id", userId)
//                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return criteriaUtil.getList(em, (builder, query, root) -> query
                .select(root)
                .where(builder.equal(root.get("user").get("id"), userId), builder.between(root.get("dateTime"), startDate, endDate))
                .orderBy(builder.desc(root.get("dateTime"))));

//        return em.createNamedQuery(Meal.ALL_SORTED_BETWEEN, Meal.class)
//                .setParameter("user_id", userId)
//                .setParameter("start_date", startDate)
//                .setParameter("end_date", endDate)
//                .getResultList();
    }
}