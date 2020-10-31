package me.project.throwingmoney.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThrowingMoneyRequestTest {


    @Test
    @DisplayName("사용자의 ID가 입력되지 않았을 경우 예외를 발생한다.")
    void check_userid_test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ThrowingMoneyRequest.of(null, "test", 3, 4);
        });
    }

    @Test
    @DisplayName("사용자의 ROOM정보가 입력되지 않았을 경우 예외를 발생한다.")
    void check_user_talking_room_test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ThrowingMoneyRequest.of(1, null, 3, 4);
        });
    }


    @Test
    @DisplayName("요청한 식별값(사용자id)는 0보다 커야한다.")
    void user_id_must_greater_than_0() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ThrowingMoneyRequest.of(0, "test", 1000, 5);
        });
    }

    @Test
    @DisplayName("금액은 나눠줄 인원보다 많아야 한다.")
    void amount_must_less_than_count() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ThrowingMoneyRequest.of(1, "test", 3, 4);
        });
    }
}