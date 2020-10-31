package me.project.throwingmoney.error;

public class TokenAlreadyExistException extends RuntimeException{

    public TokenAlreadyExistException(String message) {
        super(message);
    }

    public TokenAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected TokenAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
