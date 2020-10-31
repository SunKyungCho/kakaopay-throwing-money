package me.project.throwingmoney.dto;


import lombok.Getter;

@Getter
public class TakeMoneyResponse {

    private int takeMoney;

    public TakeMoneyResponse(int takeMoney) {
        this.takeMoney = takeMoney;
    }
}
