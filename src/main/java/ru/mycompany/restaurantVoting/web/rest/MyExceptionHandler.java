package ru.mycompany.restaurantVoting.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.mycompany.restaurantVoting.web.exceptions.IllegalRequestDataException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<String> bindValidationError(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::toString)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgumentError(IllegalRequestDataException e) {
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String otherExceptions(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void noHandlerFound() {
    }

}
