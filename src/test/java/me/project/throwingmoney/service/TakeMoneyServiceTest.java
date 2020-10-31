package me.project.throwingmoney.service;

import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.TakeMoneyRequest;
import me.project.throwingmoney.dto.TakeMoneyResponse;
import me.project.throwingmoney.error.EmptyMoneyException;
import me.project.throwingmoney.error.TokenExpireException;
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
class TakeMoneyServiceTest {

    @Autowired
    TakeMoneyService takeMoneyService;

    @Autowired
    ThrowingMoneyRepository throwingMoneyRepository;

    @Test
    @DisplayName("뿌리기 받기")
    @Transactional
    void receive_money() {
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

        //when
        TakeMoneyRequest receiveTest = TakeMoneyRequest.of(50, talkingRoom, token);
        TakeMoneyResponse receive = takeMoneyService.takeMoney(receiveTest);

        //then
        assertThat(receive).isNotNull();
    }

    @Test
    @DisplayName("자신이 뿌리기한 사용자는 받을 수 없다")
    @Transactional
    void take_thrower_excpetion() {
        //given
        String talkingRoom = "test";
        Token token = Token.of("tst");
        int thrower = 1;
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(thrower)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TakeMoneyRequest receiveTest = TakeMoneyRequest.of(thrower, talkingRoom, token);
            TakeMoneyResponse receive = takeMoneyService.takeMoney(receiveTest);
        });
    }


    @Test
    @DisplayName("만료된 Token exception")
    @Transactional
    void expired_token_exception() {
        //given
        LocalDateTime expiredDate = LocalDateTime.now().minusMinutes(10);
        String talkingRoom = "expired Token";
        Token token = Token.valueOf("tst", expiredDate);
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        //when
        Assertions.assertThrows(TokenExpireException.class, () -> {
            TakeMoneyRequest receiveTest = TakeMoneyRequest.of(50, talkingRoom, token);
            TakeMoneyResponse receive = takeMoneyService.takeMoney(receiveTest);
        });
    }

    @Test
    @DisplayName("뿌리기당 한번만 받을 수있다.")
    @Transactional
    void take_again_exception() {
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
        TakeMoneyRequest receiveTest = TakeMoneyRequest.of(50, talkingRoom, token);
        TakeMoneyResponse receive = takeMoneyService.takeMoney(receiveTest);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TakeMoneyRequest againTest = TakeMoneyRequest.of(50, talkingRoom, token);
            TakeMoneyResponse againReceive = takeMoneyService.takeMoney(againTest);
        });
    }

    @Test
    @DisplayName("돈뿌리기를 모든 사용자가 가지고 갔는데 뒤늦게 돈 받기를 할때 예외처리")
    @Transactional
    void allMoneyTakeException() {
        //given
        String talkingRoom = "test-skcho";
        Token token = Token.of("skh");
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(2)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        //when
        takeMoneyService.takeMoney(TakeMoneyRequest.of(10, talkingRoom, token));
        takeMoneyService.takeMoney(TakeMoneyRequest.of(20, talkingRoom, token));

        //then
        Assertions.assertThrows(EmptyMoneyException.class, () -> {
            takeMoneyService.takeMoney(TakeMoneyRequest.of(30, talkingRoom, token));
        });
    }


}