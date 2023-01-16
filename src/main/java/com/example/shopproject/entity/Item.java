package com.example.shopproject.entity;

import com.example.shopproject.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter @Setter
@ToString
public class Item {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length = 50)
    private String itemNm;

    @Column(name = "price",nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;
    //재고 수량
    @Lob
    @Column(nullable = false)
    private String itemDetail;
    //상품 상세 설명
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;//등록 시간

    private LocalDateTime updateTime;//수정 시간

}