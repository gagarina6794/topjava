package ru.javawebinar.topjava.exception;

public class DaoException extends RuntimeException {

    private final Integer id;

    public DaoException(String message) {
        this(message, null, null);
    }

    public DaoException(String message, Integer id) {
        super(message);
        this.id = id;
    }

    public DaoException(Exception e) {
        this(e.getMessage(), e);
    }

    public DaoException(String message, Exception e) {
        this(message, null, e);
    }

    public DaoException(String message, Integer id, Exception e) {
        super(message, e);
        this.id = id;
    }

    public Integer getUuid() {
        return id;
    }
}