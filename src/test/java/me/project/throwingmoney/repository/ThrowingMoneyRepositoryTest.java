package me.project.throwingmoney.repository;

import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ThrowingMoneyRepositoryTest {

    @Autowired
    ThrowingMoneyRepository throwingMoneyRepository;

    @Test
    @DisplayName("대화방에 해당 토큰으로 생성된 전부를 가지고 온다")
    @Transactional
    void getAllTokenTest() {
        String talkingRoom = "test-room";
        Token token = Token.of("tst");
        ThrowMoney testThrowMoney1 = ThrowMoney.builder()
                .talkingRoom(talkingRoom)
                .amount(10000)
                .divideCount(5)
                .thrower(1)
                .token(Token.of("tst"))
                .build();
        throwingMoneyRepository.save(testThrowMoney1);

        List<ThrowMoney> list = throwingMoneyRepository.findThrowMoneyByTokenAndTalkingRoom(talkingRoom, token.getValue());
        assertThat(list.size()).isEqualTo(1);
    }
}