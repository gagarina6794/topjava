package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private final static String USERS_UNIQUE_EMAIL_IDX_ERROR = "users_unique_email_idx";
    private final static String MEALS_UNIQUE_USER_DATE_TIME_IDX_ERROR = "meals_unique_user_datetime_idx";

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String error = ValidationUtil.getMessage(ValidationUtil.getRootCause(e)).toLowerCase();
        String errorMessage = null;
        if (error.contains(USERS_UNIQUE_EMAIL_IDX_ERROR)) {
            errorMessage = "User with this email already exists";
        } else if (error.contains(MEALS_UNIQUE_USER_DATE_TIME_IDX_ERROR)) {
            errorMessage = "You already have meal with this date/time";
        }
        return errorMessage != null ?
            logAndGetErrorInfo(req, e, true, VALIDATION_ERROR, errorMessage) :
            logAndGetErrorInfo(req, e, true, DATA_ERROR) ;
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class,
        BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else {
            return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
        }
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, formatBindingResult(bindingResult));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... messages) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType,
            messages.length == 0 ?
                new String[]{ValidationUtil.getMessage(rootCause)} :
                messages);
    }

    private static String[] formatBindingResult(BindingResult result) {
        List<String> messages = new ArrayList<>();
        result.getFieldErrors().forEach(
            fe -> {
                String msg = fe.getDefaultMessage();
                assert msg != null;
                if (!msg.startsWith(fe.getField())) {
                    msg = fe.getField() + ' ' + msg;
                }
                messages.add(msg);
            });
        return messages.toArray(new String[0]);
    }
}