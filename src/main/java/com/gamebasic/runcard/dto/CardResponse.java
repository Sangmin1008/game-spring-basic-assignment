package com.gamebasic.runcard.dto;

import lombok.Getter;

@Getter
public class CardResponse {
    private Long id;
    private String cardType;
    private int acquiredFloor;

    public CardResponse(Long id, String cardType, int acquiredFloor) {
        this.id = id;
        this.cardType = cardType;
        this.acquiredFloor = acquiredFloor;
    }
}
