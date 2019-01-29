package ru.mycompany.restaurantVoting.web.exceptions;

public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}
