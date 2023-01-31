package com.example.shopproject.entity;

import com.example.shopproject.constant.Role;
import com.example.shopproject.dto.MemberFromDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)//중복값 제거
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)//Enum타입을 스트링으로 변환시켜서 사용하기!
    private Role role;

    public static Member createMember(MemberFromDto memberFromDto, PasswordEncoder passwordEncoder){
        Member member=new Member();

        member.setName(memberFromDto.getName());
        member.setEmail(memberFromDto.getEmail());
        member.setPassword(member.getPassword());
        String password=passwordEncoder.encode(memberFromDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);

        return member;
    }


}
