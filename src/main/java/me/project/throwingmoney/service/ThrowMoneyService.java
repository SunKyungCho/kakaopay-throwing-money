package me.project.throwingmoney.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.ThrowingMoneyRequest;
import me.project.throwingmoney.dto.ThrowingMoneyResponse;
import me.project.throwingmoney.error.TokenAlreadyExistException;
import me.project.throwingmoney.repository.ThrowingMoneyDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThrowMoneyService {

    private final ThrowingMoneyDao throwingMoneyDao;
    private final TokenGenerator tokenGenerator;

    /*
    * 돈뿌리기를 생성한다.
    * */
    @Transactional
    public ThrowingMoneyResponse register(ThrowingMoneyRequest request) {
        Token token = tokenGenerator.create();
        if (throwingMoneyDao.hasTokenInTalkingRoom(token, request.getTalkingRoom())) {
            throw new TokenAlreadyExistException("Token already exists. token:" + token.getValue());
        }
        ThrowMoney throwMoney = createThrowMoney(request, token);
        throwingMoneyDao.save(throwMoney);
        return new ThrowingMoneyResponse(token);
    }

    private ThrowMoney createThrowMoney(ThrowingMoneyRequest request, Token token) {
        return ThrowMoney.builder()
                .thrower(request.getUserId())
                .talkingRoom(request.getTalkingRoom())
                .amount(request.getAmount())
                .divideCount(request.getDivideCount())
                .token(token)
                .build();
    }
}
