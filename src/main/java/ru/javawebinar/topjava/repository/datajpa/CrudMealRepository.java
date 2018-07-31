package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    Optional<Meal> findByIdAndUserId(int id, int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user u WHERE m.id=:id AND u.id=:userId")
    Meal getWithUser(@Param("id") int id, @Param("userId") int userId);
}
