package com.example.shopproject.Service;

import com.example.shopproject.dto.MemberFromDto;
import com.example.shopproject.entity.Member;
import com.example.shopproject.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.test.properties")
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFromDto memberFromDto=new MemberFromDto();
        memberFromDto.setEmail("launcher37@naver.com");
        memberFromDto.setName("주동호");
        memberFromDto.setAddress("인천광역시 주안동");
        memberFromDto.setPassword("1234");
        return Member.createMember(memberFromDto,passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member=createMember();

        Member saveMember=memberService.saveMember(member);

        assertEquals(member.getEmail(),saveMember.getEmail());
        assertEquals(member.getName(),saveMember.getName());
        assertEquals(member.getAddress(),saveMember.getAddress());
        assertEquals(member.getPassword(),saveMember.getPassword());
        assertEquals(member.getRole(),saveMember.getRole());

    }
}
