package me.project.throwingmoney.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.project.throwingmoney.error.EmptyMoneyException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ThrowMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int thrower;
    private int amount;
    private int divideCount;
    private String talkingRoom;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id")
    private final List<Money> moneys = new ArrayList<>();
    @Version
    private int version;
    @Embedded
    private Token token;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public ThrowMoney(int thrower, int amount, int divideCount, String talkingRoom, Token token) {
        this.thrower = thrower;
        this.amount = amount;
        this.divideCount = divideCount;
        this.token = token;
        this.talkingRoom = talkingRoom;
        divideMoney(amount, divideCount);
    }

    private void divideMoney(int amount, int divideCount) {
        int restMoney = amount;
        for (int i = 1; i < divideCount; i++) {
            int maxDivideMoney = restMoney - (divideCount - i);
            int divideMoney = getRandomDivideMoney(maxDivideMoney);
            moneys.add(Money.valueOf(divideMoney));
            restMoney -= divideMoney;
        }
        moneys.add(Money.valueOf(restMoney));
    }

    private int getRandomDivideMoney(int max) {
        double randomValue = Math.random();
        return (int) (randomValue * max) + 1;
    }

    public boolean isExpired() {
        return token.isExpired();
    }

    public int throwMoneyToUser(int userId) {
        verifyAlreadyTakeMoneyByUserId(userId);
        verifyTakeMoneyByThrower(userId);
        Money money = getMoney();
        return money.takeMoneyByUser(userId);
    }

    private Money getMoney() {
        List<Money> restMoneys = moneys.stream()
                .filter(money -> !money.isTook())
                .collect(Collectors.toList());
        if (restMoneys.size() == 0) {
            throw new EmptyMoneyException("There's nothing left.");
        }
        return restMoneys.get(0);
    }

    private void verifyTakeMoneyByThrower(int userId) {
        if (thrower == userId) {
            throw new IllegalArgumentException("Cannot receive yourself.");
        }
    }

    private void verifyAlreadyTakeMoneyByUserId(int userId) {
        List<Money> restMoneys = moneys.stream()
                .filter(money -> money.isTookByUser(userId))
                .collect(Collectors.toList());
        if (restMoneys.size() > 0) {
            throw new IllegalArgumentException("User already received. userID: " + userId);
        }
    }

    public List<Money> getMoneys() {
        return moneys;
    }

    public boolean isThrower(int userId) {
        return this.thrower == userId;
    }

    public boolean canReadStat() {
        return token.canReadStat();
    }
}
