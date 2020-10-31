package me.project.throwingmoney.service;

import lombok.RequiredArgsConstructor;
import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.dto.ThrowMoneyStatReadRequest;
import me.project.throwingmoney.dto.ThrowMoneyStateResponse;
import me.project.throwingmoney.error.EmptyMoneyException;
import me.project.throwingmoney.repository.ThrowingMoneyDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateService {

    private final ThrowingMoneyDao throwingMoneyDao;

    public ThrowMoneyStateResponse getState(ThrowMoneyStatReadRequest request) {
        ThrowMoney validThrowMoney = throwingMoneyDao.getThrowMoneyState(request.getTalkingRoom(), request.getToken());
        if (validThrowMoney == null) {
            throw new EmptyMoneyException("There is no throw money. ");
        }
        verifyThrower(validThrowMoney, request.getUserId());
        return ThrowMoneyStateResponse.createFromEntity(validThrowMoney);
    }

    private void verifyThrower(ThrowMoney validThrowMoney, int userId) {
        if (!validThrowMoney.isThrower(userId)) {
            throw new IllegalArgumentException("No read permission. ");
        }
    }
}