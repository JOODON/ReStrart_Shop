package com.example.shopproject.Service;

import com.example.shopproject.constant.ItemSellStatus;
import com.example.shopproject.constant.OrderStatus;
import com.example.shopproject.dto.OrderDto;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.Member;
import com.example.shopproject.entity.Order;
import com.example.shopproject.entity.OrderItem;
import com.example.shopproject.repository.ItemRepository;
import com.example.shopproject.repository.MemberRepository;
import com.example.shopproject.repository.OrderRepository;
import com.example.shopproject.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.test.properties")
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;

    public Item saveItem(){
        Item item=new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){
     Member member=new Member();
     member.setEmail("test@test.com");
     return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item=saveItem();
        Member member=saveMember();

        OrderDto orderDto=new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId= orderService.order(orderDto, member.getEmail());

        Order order=orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems=order.getOrderItems();

        int totalPrice= orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice,order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder(){
        Item item=saveItem();
        Member member=saveMember();

        OrderDto orderDto=new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());
        Long orderId=orderService.order(orderDto,member.getEmail());

        Order order=orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        assertEquals(OrderStatus.CANCEL , order.getOrderStatus());
        assertEquals(100,item.getStockNumber());

    }
}
