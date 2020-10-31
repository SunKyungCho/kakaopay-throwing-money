package me.project.throwingmoney.error;

/*
* 돈뿌리기 이후 뿌려진 돈들을 사용자가 모두 가지고 가 남은 할당이 없을때 발생하는 Excption이다.
* */
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
