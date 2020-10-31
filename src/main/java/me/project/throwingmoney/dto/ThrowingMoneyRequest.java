package me.project.throwingmoney.dto;

import org.springframework.util.Assert;

public class ThrowingMoneyRequest {

    private int userId;
    private String talkingRoom;
    private int amount;
    private int divideCount;

    private ThrowingMoneyRequest(int userId, String talkingRoom, int amount, int divideCount) {
        this.userId = userId;
        this.talkingRoom = talkingRoom;
        this.amount = amount;
        this.divideCount = divideCount;
    }

    public static ThrowingMoneyRequest of(Integer userId, String talkingRoom, int amount, int divideCount) {
        Assert.notNull(userId, "X-USER-ID argument is required; it must not be null");
        Assert.notNull(talkingRoom, "X-ROOM-ID argument is required; it must not be null");
        Assert.state(amount > 0, "amount parameter is required; it must not be null");
        Assert.state(divideCount > 0, "divideCount parameter is required; it must not be null");
        verifyUserId(userId);
        verifyDivide(amount, divideCount);

        return new ThrowingMoneyRequest(userId, talkingRoom, amount, divideCount);
    }

    private static void verifyUserId(int userId) {
        if(userId < 1) {
            throw new IllegalArgumentException("User ID must be greater than 0; current value is +" + userId);
        }
    }

    private static void verifyDivide(int amount, int divideCount) {
        if (amount < divideCount) {
            throw new IllegalArgumentException("Amount can not be less than divideCount");
        }
    }
}
