package me.project.throwingmoney.service;

import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.ThrowMoneyStatReadRequest;
import me.project.throwingmoney.dto.ThrowMoneyStateResponse;
import me.project.throwingmoney.error.StateTokenExpireException;
import me.project.throwingmoney.repository.ThrowingMoneyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StateServiceTest {

    @Autowired
    ThrowingMoneyRepository throwingMoneyRepository;

    @Autowired
    StateService stateService;

    @Test
    @Transactional
    @DisplayName("뿌리기 상태 정보 가지고 오기")
    void read_throw_money_stat() {
        //given
        String talkingRoom = "test";
        Token token = Token.of("tst");
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        ThrowMoneyStatReadRequest readRequest = ThrowMoneyStatReadRequest.of(1, talkingRoom, token);

        //when
        ThrowMoneyStateResponse state = stateService.getState(readRequest);

        //then
        assertThat(state).isNotNull();
        assertThat(state.getAmount()).isEqualTo(10000);
        assertThat(state.getDivideCount()).isEqualTo(5);
        assertThat(state.getThrower()).isEqualTo(1);
        assertThat(state.getToken()).isEqualTo(token);
    }

    @Test
    @Transactional
    @DisplayName("조회가 만료된 경우 Exception을 발생 시킨다")
    void read_throw_money_stat_with_expired_token() {
        //given
        String talkingRoom = "test-stat-exception";
        LocalDateTime expiredDate = LocalDateTime.now().minusDays(10);
        Token token = Token.valueOf("tst", expiredDate);
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        ThrowMoneyStatReadRequest readRequest = ThrowMoneyStatReadRequest.of(1, talkingRoom, token);

        //then
        Assertions.assertThrows(StateTokenExpireException.class, () -> {
            ThrowMoneyStateResponse state = stateService.getState(readRequest);
        });
    }
}