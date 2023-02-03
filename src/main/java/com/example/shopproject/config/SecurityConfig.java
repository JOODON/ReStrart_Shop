package com.example.shopproject.config;


import com.example.shopproject.entity.Member;
import com.example.shopproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;


    @Override
    //Http요청 로그인 로그아웃 기능을 여기에서 구현시키는곳
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin().loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

    http.authorizeRequests().//시큐리티에 서블릿 리쿼스트를 이용한다는것을 의미함
            mvcMatchers("/","/members/**","/item/**","images/**")
            .permitAll().//여기까지 모든 사용자가 사용가능
            mvcMatchers("/admin/**").hasRole("ADMIN")//여기는 어드민만 사용가능
            .anyRequest().authenticated();

    http.exceptionHandling()
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    //인증되지 않는경우 처리해주기
    }
    @Bean
    //비밀번호를 한번 더 암호화 시켜서 잠궈주는 부분
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    //static 파일 경로들은 마시하도록 설정해둠!
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService)
        .passwordEncoder(passwordEncoder());
        //userDetailsService를 구현하는 객체로 memberService를넣어줌
    }

}
