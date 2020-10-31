package me.project.throwingmoney.service;

import me.project.throwingmoney.domain.Token;
import org.springframework.stereotype.Component;

@Component
public class DefaultTokenGenerator implements TokenGenerator {

    private static final char[] TOKEN_RESOURCES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'Y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    @Override
    public Token create() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int randomIndex = getRandomIndex();
            stringBuilder.append(TOKEN_RESOURCES[randomIndex]);
        }
        return Token.of(stringBuilder.toString());
    }

    private int getRandomIndex() {
        double randomValue = Math.random();
        return (int) (randomValue * 61);
    }
}