package com.example.shopproject.service;

import com.example.shopproject.dto.CartItemDto;
import com.example.shopproject.entity.Cart;
import com.example.shopproject.entity.CartItem;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.Member;
import com.example.shopproject.repository.CartItemRepository;
import com.example.shopproject.repository.CartRepository;
import com.example.shopproject.repository.ItemRepository;
import com.example.shopproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto,String email){
        Item item=itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member=memberRepository.findByEmail(email);

        Cart cart=cartRepository.findByMemberId(member.getId());
        if (cart == null){
            cart=Cart.createCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartItem=cartItemRepository.findByCartIdAndItemId(cart.getId(),item.getId());
        if (savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        }
        else {
            CartItem cartItem=CartItem.createCartItem(cart,item,cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
