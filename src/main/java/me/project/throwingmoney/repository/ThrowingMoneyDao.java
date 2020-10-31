package me.project.throwingmoney.repository;


import lombok.RequiredArgsConstructor;
import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThrowingMoneyDao {

    private final ThrowingMoneyRepository throwingMoneyRepository;

    public void save(ThrowMoney throwMoney) {
        throwingMoneyRepository.save(throwMoney);
    }

    public boolean hasTokenInTalkingRoom(Token token, String talkingRoom) {
        List<ThrowMoney> throwMoneys = throwingMoneyRepository.findThrowMoneyByTokenAndTalkingRoom(talkingRoom, token.getValue());
        long validCount = throwMoneys.stream()
                .filter(x -> !x.isExpired())
                .count();
        return validCount > 0;
    }

}
