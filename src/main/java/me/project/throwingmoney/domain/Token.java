package me.project.throwingmoney.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Token {

    @Column(name = "token")
    private String value;
    private LocalDateTime expireTime;

    private Token(String value) {
        this.value = value;
        this.expireTime = LocalDateTime.now().plusMinutes(10);
    }

    private Token(String value, LocalDateTime expireTime) {
        this.value = value;
        this.expireTime = expireTime;
    }

    public static Token valueOf(String value, LocalDateTime expireTime) {
        verify(value);
        return new Token(value, expireTime);
    }

    public static Token of(String value) {
        verify(value);
        return new Token(value);
    }

    private static void verify(String value) {
        if (value.length() != 3) {
            throw new IllegalArgumentException("token length must be 3. current token length: " + value);
        }
    }

    public boolean canReadStat() {
        return LocalDateTime.now().isAfter(expireTime.plusDays(7));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
