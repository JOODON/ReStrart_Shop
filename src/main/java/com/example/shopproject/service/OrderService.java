package com.example.shopproject.service;

import com.example.shopproject.dto.OrderDto;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.Member;
import com.example.shopproject.entity.Order;
import com.example.shopproject.entity.OrderItem;
import com.example.shopproject.repository.ItemRepository;
import com.example.shopproject.repository.MemberRepository;
import com.example.shopproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto,String email){
        Item item=itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        Member member=memberRepository.findByEmail(email);

        List<OrderItem> orderItemList=new ArrayList<>();

        OrderItem orderItem=OrderItem.createOrderItem(item, orderDto.getCount());

        orderItemList.add(orderItem);

        Order order=Order.createOrder(member,orderItemList);
        orderRepository.save(order);

        return order.getId();
    }


}
