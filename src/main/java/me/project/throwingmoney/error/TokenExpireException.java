package me.project.throwingmoney.error;

public class TokenExpireException extends RuntimeException {

    public TokenExpireException(String message) {
        super(message);
    }
}