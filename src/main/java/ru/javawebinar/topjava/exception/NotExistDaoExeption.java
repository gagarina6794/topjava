package ru.javawebinar.topjava.exception;

public class NotExistDaoExeption extends DaoException {

    public NotExistDaoExeption(Integer id) {
        super("Meal " + id + " not exist", id);
    }
}