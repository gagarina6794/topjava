package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

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
        List<Meal> mealList = getList(em,userId,id,null,null,false);
        return DataAccessUtils.singleResult(mealList);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getList(em,userId,0,null,null,false);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getList(em,userId,0,startDate,endDate,true);
    }
    private List<Meal> getList(EntityManager em, int userId, int id, LocalDateTime startDate, LocalDateTime endDate, boolean filter) {
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