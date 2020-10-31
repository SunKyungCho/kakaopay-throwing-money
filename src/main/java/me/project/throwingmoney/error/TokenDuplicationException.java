package me.project.throwingmoney.error;

public class TokenDuplicationException extends RuntimeException {
    public TokenDuplicationException(String message) {
        super(message);
    }
}
