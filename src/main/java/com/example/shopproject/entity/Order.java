package com.example.shopproject.entity;

import com.example.shopproject.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order{
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)//주인 찾아주기
    //영속성도 다시 지정
    private List<OrderItem> orderItems=new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
