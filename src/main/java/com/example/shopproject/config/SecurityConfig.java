package com.example.shopproject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    //Http요청 로그인 로그아웃 기능을 여기에서 구현시키는곳
    protected void configure(HttpSecurity http) throws Exception{

    }

    @Bean
    //비밀번호를 한번 더 암호화 시켜서 잠궈주는 부분
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
