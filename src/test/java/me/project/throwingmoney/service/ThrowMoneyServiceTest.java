package me.project.throwingmoney.service;

import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.ThrowingMoneyRequest;
import me.project.throwingmoney.dto.ThrowingMoneyResponse;
import me.project.throwingmoney.error.TokenAlreadyExistException;
import me.project.throwingmoney.repository.ThrowingMoneyDao;
import me.project.throwingmoney.repository.ThrowingMoneyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ThrowMoneyServiceTest {

    @Autowired
    ThrowingMoneyDao throwingMoneyDao;

    @Autowired
    ThrowingMoneyRepository repository;

    ThrowMoneyService throwMoneyService;
    TokenGenerator tokenGenerator;

    @BeforeEach
    void setup() {
        tokenGenerator = () -> Token.of("hel");
        throwMoneyService = new ThrowMoneyService(throwingMoneyDao, () -> Token.of("hel"));
    }

    @Test
    @DisplayName("돈뿌기 등록")
    @Transactional
    void throwMoneyTest() {

        String talkingRoom = "test";
        //given
        ThrowingMoneyRequest request = ThrowingMoneyRequest.of(1, talkingRoom, 10000, 5);

        //when
        ThrowingMoneyResponse response = throwMoneyService.register(request);
        Token token = response.getToken();

        //then
        ThrowMoney throwMoney = repository.findThrowMoneyByTokenAndTalkingRoomInValid(LocalDateTime.now(), token.getValue(), talkingRoom);
        assertThat(throwMoney).isNotNull();
    }

    @Test
    @DisplayName("같은 대화룸에 이미 생성된 토큰으로 토큰이 생성되었다면 예외를 발생한다")
    @Transactional
    void wrongCreateTokenException() {

        //given
        String talkingRoom = "test";
        ThrowMoney expiredThrowMoney = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(Token.of("hel"))
                .build();
        repository.save(expiredThrowMoney);

        ThrowingMoneyRequest request = ThrowingMoneyRequest.of(1, talkingRoom, 10000, 5);

        //when
        Assertions.assertThrows(TokenAlreadyExistException.class, () -> throwMoneyService.register(request));
    }

    @Test
    @DisplayName("같은 대화룸에 이미 생성된 토큰이 있지만 expired 되었다면 토큰이 생성되었다면 생성할 수 있다")
    @Transactional
    void expiredTokenCreateTest() {

        //given
        String talkingRoom = "test";
        ThrowMoney expiredThrowMoney = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(Token.valueOf("hel", LocalDateTime.now().minusMinutes(10)))
                .build();
        repository.save(expiredThrowMoney);
        ThrowingMoneyRequest request = ThrowingMoneyRequest.of(1, talkingRoom, 10000, 5);

        //when
        ThrowingMoneyResponse response = throwMoneyService.register(request);
        Token token = response.getToken();

        //then
        ThrowMoney throwMoney = repository.findThrowMoneyByTokenAndTalkingRoomInValid(LocalDateTime.now(), token.getValue(), talkingRoom);
        assertThat(throwMoney).isNotNull();
    }
}
