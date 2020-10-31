package me.project.throwingmoney.repository;

import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}