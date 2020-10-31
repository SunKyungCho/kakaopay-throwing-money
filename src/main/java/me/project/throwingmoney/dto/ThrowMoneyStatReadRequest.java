package me.project.throwingmoney.dto;

import lombok.Getter;
import me.project.throwingmoney.domain.Token;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
public class ThrowMoneyStatReadRequest {

    private final int userId;
    private final String talkingRoom;
    private final Token token;

    public ThrowMoneyStatReadRequest(Integer userId, String talkingRoom, Token token) {
        this.userId = userId;
        this.talkingRoom = talkingRoom;
        this.token = token;
    }

    public static ThrowMoneyStatReadRequest of(Integer userId, String talkingRoom, Token token) {
        Assert.notNull(userId, "X-USER-ID argument is required; it must not be null");
        Assert.notNull(talkingRoom, "X-ROOM-ID argument is required; it must not be null");
        Assert.notNull(token, "Token argument is required; it must not be null");
        verifyUserId(userId);
        return new ThrowMoneyStatReadRequest(userId, talkingRoom, token);
    }

    private static void verifyUserId(int userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("User ID must be greater than 0; current value is +" + userId);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThrowMoneyStatReadRequest that = (ThrowMoneyStatReadRequest) o;
        return userId == that.userId &&
                Objects.equals(talkingRoom, that.talkingRoom) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, talkingRoom, token);
    }
}
