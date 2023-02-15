package com.example.shopproject.Service;

import com.example.shopproject.entity.Member;
import com.example.shopproject.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.test.properties")
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Audting테스트")
    @WithMockUser(username = "Dongho",roles = "USER")
    public void auditingTest(){
        Member newMember=new Member();
        memberRepository.save(newMember);

        em.flush();
        em.clear();

        Member member=memberRepository.findById(newMember.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("register TIME :"+member.getRegTime());
        System.out.println("update TIME :"+member.getUpDateTime());
        System.out.println("create Member :"+member.getCreatedBy());
        System.out.println("modify member :"+member.getModifiedyBy());

    }
}
