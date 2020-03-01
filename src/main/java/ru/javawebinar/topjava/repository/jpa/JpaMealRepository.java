package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Meal save(Meal meal, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            entityManager.persist(meal);
            return meal;
        } else {
            if (get(meal.getId(), userId) != null) {
                return entityManager.merge(meal);
            }
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter(1, userId)
                .setParameter(2, id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> mealList = entityManager.createNamedQuery(Meal.GET, Meal.class)
                .setParameter(1, userId)
                .setParameter(2, id)
                .getResultList();
        return DataAccessUtils.singleResult(mealList);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.ALL, Meal.class)
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(Meal.GET_BETWEEN_HALF_OPEN, Meal.class)
                .setParameter(1, userId)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getResultList();
    }
}