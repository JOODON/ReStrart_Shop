package com.example.shopproject.dto;

import com.example.shopproject.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter @Setter
public class OrderItemDto {
    public OrderItemDto(OrderItem orderItem,String imgUrl){
        this.itemNm=orderItem.getItem().getItemNm();
        this.count=orderItem.getCount();
        this.orderPrice=orderItem.getOrderPrice();
        this.imgUrl=imgUrl;
    }
    private String itemNm;

    private int count;

    private int orderPrice;

    private String imgUrl;
}
