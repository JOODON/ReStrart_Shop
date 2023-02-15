package com.example.shopproject.dto;

import com.example.shopproject.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
    private String searchDateType;
    //날짜 시간에 따른 분류
    private ItemSellStatus itemSellStatus;
    //상품 판매기준등록
    private String searchBy;
    //어떤 유형으로 검색할지
    private String searchQuery="";



}
