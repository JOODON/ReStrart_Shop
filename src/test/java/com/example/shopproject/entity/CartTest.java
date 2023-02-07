package com.example.shopproject.entity;

import com.example.shopproject.dto.MemberFormDto;
import com.example.shopproject.repository.CartRepository;
import com.example.shopproject.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.test.properties")
class CartTest{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember(){
        MemberFormDto memberFormDto=new MemberFormDto();
        memberFormDto.setEmail("test@gmail.com");
        memberFormDto.setName("주동호");
        memberFormDto.setAddress("인천광역시 남동구");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto,passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 맵핑 테스트")
    public void findCartAndMemberTest(){
        Member member=createMember();
        memberRepository.save(member);

        Cart cart=new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();//데이터 베이스에 반영
        em.clear();

        Cart saveCart=cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(saveCart.getMember().getId(),member.getId());
    }
}
