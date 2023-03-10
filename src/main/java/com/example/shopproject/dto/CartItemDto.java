package com.example.shopproject.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CartItemDto {

    @NotNull(message = "상품 아이디는 필수값 입니다")
    private Long itemId;

    @Min(value = 1,message = "최소 1개 이상은 담아주새요")
    private int count;
}
