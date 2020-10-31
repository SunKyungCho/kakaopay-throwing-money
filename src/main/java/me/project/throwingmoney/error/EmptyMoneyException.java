package me.project.throwingmoney.error;

public class EmptyMoneyException extends RuntimeException {

    public EmptyMoneyException(String message) {
        super(message);
    }

    public EmptyMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyMoneyException(Throwable cause) {
        super(cause);
    }

    protected EmptyMoneyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
