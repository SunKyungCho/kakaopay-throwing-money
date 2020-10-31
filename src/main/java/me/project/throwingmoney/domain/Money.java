package me.project.throwingmoney.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long moneyId;
    private int value;
    private int userId;

    private Money(int value) {
        this.value = value;
    }

    public static Money valueOf(int value) {
        return new Money(value);
    }

    public int takeMoneyByUser(int userId) {
        this.userId = userId;
        return this.value;
    }

    public boolean isTook() {
        return userId != 0;
    }

    public boolean isTookByUser(int userId) {
        return this.userId == userId;
    }

    public int getValue() {
        return value;
    }

    public int getUserId() {
        return userId;
    }
}
