package me.project.throwingmoney.error;

public class StateTokenExpireException extends RuntimeException {

    public StateTokenExpireException(String message) {
        super(message);
    }
}
