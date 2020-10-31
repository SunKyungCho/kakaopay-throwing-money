package me.project.throwingmoney.error;

public class ThrowingMoneyNotFoundException extends RuntimeException{

    public ThrowingMoneyNotFoundException(String message) {
        super(message);
    }
}
