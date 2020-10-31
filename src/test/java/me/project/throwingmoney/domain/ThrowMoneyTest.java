package me.project.throwingmoney.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ThrowMoneyTest {

    @Test
    @DisplayName("돈뿌리기 생성시 돈을 5개로 나눈다")
    void dvideTest() {

        int amount = 10;
        ThrowMoney test = ThrowMoney.builder()
                .token(Token.of("tee"))
                .amount(amount)
                .divideCount(5)
                .thrower(1)
                .build();
        List<Money> moneys = test.getMoneys();
        int totalMoney = moneys.stream().mapToInt(x -> x.getValue()).sum();
        assertThat(moneys.size()).isEqualTo(5);
        assertThat(totalMoney).isEqualTo(amount);
    }

    @Test
    @DisplayName("user 가 가져간다. ")
    void takeMoneuy() {
        ThrowMoney test = ThrowMoney.builder()
                .token(Token.of("tee"))
                .amount(10)
                .divideCount(5)
                .thrower(1)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            int one = test.throwMoneyToUser(1);
            int two = test.throwMoneyToUser(1);
        });

    }

}