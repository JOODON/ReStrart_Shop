package com.example.shopproject.entity;

import com.example.shopproject.constant.ItemSellStatus;
import com.example.shopproject.dto.ItemFormDto;
import com.example.shopproject.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter @Setter
@ToString
public class Item extends BaseEntity{
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

    public void updateTime(ItemFormDto itemFormDto){
        this.itemNm=itemFormDto.getItemNm();
        this.price=itemFormDto.getPrice();
        this.stockNumber=itemFormDto.getStockNumber();
        this.itemDetail=itemFormDto.getItemDetail();
        this.itemSellStatus=itemFormDto.getItemSellStatus();
    }
    public void removeStock(int stockNumber){
        int restStock=this.stockNumber - stockNumber;
        if (restStock<0){
            throw new OutOfStockException("상품의 제고가 부족합니다. 현재 재고 수량:"+this.stockNumber + ")");
        }
        this.stockNumber= restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}