package me.project.throwingmoney.dto;

import lombok.Getter;
import me.project.throwingmoney.domain.Token;

@Getter
public class ThrowingMoneyResponse {

    private final Token token;

    public ThrowingMoneyResponse(Token token) {
        this.token = token;
    }
}
