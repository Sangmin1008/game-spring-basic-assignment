package com.gamebasic.runcard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RunCardRequest {
    @NotBlank(message = "카드 타입은 필수 입력값입니다.")
    private String cardType;

    @NotNull(message = "획득 층수는 필수 입력갑입니다.")
    @Min(value = 0, message = "획득 층수는 0 이상이어야 합니다.")
    @Max(value = 10, message = "획득 층수는 10 이하이어야 합니다.")
    private Integer acquiredFloor;
}
