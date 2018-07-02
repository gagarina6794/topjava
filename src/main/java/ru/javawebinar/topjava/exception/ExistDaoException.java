package ru.javawebinar.topjava.exception;

public class ExistDaoException extends DaoException {

    public ExistDaoException(Integer id) {
        super("Meal " + id + " already exist", id);
    }
}