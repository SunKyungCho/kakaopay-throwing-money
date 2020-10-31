package me.project.throwingmoney.repository;

import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.error.ThrowingMoneyNotFoundException;
import me.project.throwingmoney.error.TokenExpireException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ThrowingMoneyDaoTest {

    @Autowired
    ThrowingMoneyRepository throwingMoneyRepository;

    @Autowired
    ThrowingMoneyDao throwingMoneyDao;

    @Test
    @DisplayName("대화방에 이미 생성된 토큰이 있다면 True를 리턴한다")
    void duplication_token_in_same_talking_room() {
        //given
        Token token = Token.of("tst");
        String talkingRoom = "test";
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        boolean hasTokenInTalkingRoom = throwingMoneyDao.hasTokenInTalkingRoom(token, talkingRoom);
        assertThat(hasTokenInTalkingRoom).isTrue();
    }

    @Test
    @DisplayName("대화방에 돈뿌리기 정보 가지고 오기")
    @Transactional
    void getThrowingMoneyTest() {
        //given
        Token token = Token.of("tst");
        String talkingRoom = "test";
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        // than
        ThrowMoney throwMoney = throwingMoneyDao.findThrowMoney(talkingRoom, token);
        assertThat(throwMoney).isNotNull();
    }

    @Test
    @DisplayName("대화방에 돈뿌리기일시 만료되었다는 예외를 발생시킨다.")
    @Transactional
    void getThrowingMoneyExpiredExceptionTest() {
        //given
        Token token = Token.valueOf("tst", LocalDateTime.now().minusMinutes(10));
        String talkingRoom = "test";
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        // than
        Assertions.assertThrows(TokenExpireException.class, () -> throwingMoneyDao.findThrowMoney(talkingRoom, token));
    }

    @Test
    @DisplayName("대화방에 존재하지 않는 돈뿌리기를 찾으려하면 예외를 발생시킨다.")
    @Transactional
    void throwingMoneyNotFoundExceptionTest() {
        //given
        Token token = Token.valueOf("2k3", LocalDateTime.now().minusMinutes(10));
        String talkingRoom = "test0-tst";

        // than
        Assertions.assertThrows(ThrowingMoneyNotFoundException.class, () -> throwingMoneyDao.findThrowMoney(talkingRoom, token));
    }

    @Test
    @DisplayName("대화방에 돈뿌리기 조회를 한다")
    @Transactional
    void throwingMoneyStateTest() {
        //given
        Token token = Token.valueOf("2k3", LocalDateTime.now().minusMinutes(10));
        String talkingRoom = "test0-tst";
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(token)
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        // than
        ThrowMoney throwMoneyState = throwingMoneyDao.getThrowMoneyState(talkingRoom, token);
        System.out.println();
    }

}