package me.project.throwingmoney.dto;

import lombok.Getter;
import me.project.throwingmoney.domain.Money;
import me.project.throwingmoney.domain.ThrowMoney;
import me.project.throwingmoney.domain.Token;

import java.util.List;

@Getter
public class ThrowMoneyStateResponse {

    private final int thrower;
    private final int amount;
    private final int divideCount;
    private final String talkingRoom;
    private final Token token;
    private final List<Money> moneys;

    public ThrowMoneyStateResponse(int thrower, int amount, int divideCount, String talkingRoom, Token token, List<Money> moneys) {
        this.thrower = thrower;
        this.amount = amount;
        this.divideCount = divideCount;
        this.talkingRoom = talkingRoom;
        this.token = token;
        this.moneys = moneys;
    }

    public static ThrowMoneyStateResponse createFromEntity(ThrowMoney throwMoney) {
        return new ThrowMoneyStateResponse(
                throwMoney.getThrower(),
                throwMoney.getAmount(),
                throwMoney.getDivideCount(),
                throwMoney.getTalkingRoom(),
                throwMoney.getToken(),
                throwMoney.getMoneys()
        );
    }
}
