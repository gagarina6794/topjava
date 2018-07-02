package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.exception.ExistDaoException;
import ru.javawebinar.topjava.exception.NotExistDaoExeption;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractMealDao implements Dao {
    private static final Logger LOG = getLogger(AbstractMealDao.class);

    protected abstract Integer getIndex(Integer id);

    protected abstract boolean checkIndex(Integer index);

    protected abstract Stream<Meal> getStream();

    protected abstract void doSave(Meal meal, Integer index);

    protected abstract Meal doGet(Integer index);

    protected abstract void doUpdate(Meal meal, Integer index);

    protected abstract void doDelete(Integer index);

    @Override
    public void save(Meal meal) {
        LOG.info("Save " + meal);
        Objects.requireNonNull(meal, "Bad news, we received null for save!");
        Integer index = getNotExistedIndex(meal.getId());
        doSave(meal, index);
    }

    @Override
    public Meal get(Integer id) {
        LOG.info("Get " + id);
        Integer index = getExistedIndex(id);
        return doGet(index);
    }

    @Override
    public void update(Meal meal) {
        LOG.info("Update " + meal);
        Objects.requireNonNull(meal, "Bad news, we received null for update!");
        Integer index = getExistedIndex(meal.getId());
        doUpdate(meal, index);
    }

    @Override
    public void delete(Integer id) {
        LOG.info("Delete " + id);
        Integer index = getExistedIndex(id);
        doDelete(index);
    }

    @Override
    public List<Meal> getAll() {
        LOG.info("getAllSorted");
        return getStream().collect(Collectors.toList());
    }

    private Integer getExistedIndex(Integer id) {
        Integer index = getIndex(id);
        if (!checkIndex(index)) {
            LOG.debug("Meal " + id + " not exist");
            throw new NotExistDaoExeption(id);
        }
        return index;
    }

    private Integer getNotExistedIndex(Integer id) {
        Integer index = getIndex(id);
        if (checkIndex(index)) {
            LOG.debug("Meal " + id + " already exist");
            throw new ExistDaoException(id);
        }
        return index;
    }
}
