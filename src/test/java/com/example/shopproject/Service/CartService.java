package com.example.shopproject.Service;

import com.example.shopproject.constant.ItemSellStatus;
import com.example.shopproject.dto.CartItemDto;
import com.example.shopproject.entity.CartItem;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.Member;
import com.example.shopproject.repository.CartItemRepository;
import com.example.shopproject.repository.CartRepository;
import com.example.shopproject.repository.ItemRepository;
import com.example.shopproject.repository.MemberRepository;
import groovy.transform.Trait;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.test.properties")
public class CartService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    com.example.shopproject.service.CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

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
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Item item=saveItem();
        Member member=saveMember();

        CartItemDto cartItemDto=new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId= cartService.addCart(cartItemDto,member.getEmail());
        CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(),cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(),cartItem.getCount());
    }
}
