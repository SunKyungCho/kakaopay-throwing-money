package me.project.throwingmoney.dto;

import lombok.Getter;
import me.project.throwingmoney.domain.Token;
import org.springframework.util.Assert;

@Getter
public class TakeMoneyRequest {
    private final Integer userId;
    private final String talkingRoom;
    private final Token token;

    private TakeMoneyRequest(Integer userId, String talkingRoom, Token token) {
        this.userId = userId;
        this.talkingRoom = talkingRoom;
        this.token = token;
    }

    public static TakeMoneyRequest of(Integer userId, String talkingRoom, Token token) {
        Assert.notNull(userId, "X-USER-ID argument is required; it must not be null");
        Assert.notNull(talkingRoom, "X-ROOM-ID argument is required; it must not be null");
        Assert.notNull(token, "Token argument is required; it must not be null");
        verifyUserId(userId);
        return new TakeMoneyRequest(userId, talkingRoom, token);
    }

    private static void verifyUserId(int userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("User ID must be greater than 0; current value is +" + userId);
        }
    }
}
