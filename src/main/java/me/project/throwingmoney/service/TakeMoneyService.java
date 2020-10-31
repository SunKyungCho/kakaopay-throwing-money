package me.project.throwingmoney.service;

import lombok.RequiredArgsConstructor;
import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.dto.TakeMoneyRequest;
import me.project.throwingmoney.dto.TakeMoneyResponse;
import me.project.throwingmoney.repository.ThrowingMoneyDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TakeMoneyService {

    private final ThrowingMoneyDao throwingMoneyDao;

    @Transactional
    public TakeMoneyResponse takeMoney(TakeMoneyRequest request) {
        ThrowMoney validThrowMoney = throwingMoneyDao.findThrowMoney(request.getTalkingRoom(), request.getToken());
        int takeMoney = validThrowMoney.throwMoneyToUser(request.getUserId());
        return new TakeMoneyResponse(takeMoney);
    }
}
