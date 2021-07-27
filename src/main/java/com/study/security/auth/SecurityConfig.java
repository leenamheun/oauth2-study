package com.study.security.auth;

import com.study.security.auth.service.CustomOAuth2UserService;
import com.study.security.auth.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor //초기화 되지 않은 Final,@Notnull필드에 생성자 생성 -> autowired없이 사용가능
@EnableWebSecurity //active webSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests() //Url별 권한 관리 시작
                .antMatchers("/login").permitAll()// "/login"로 지정된 url은 전체 열람 권한(로그인 페이지는 모두 열람가능)
                .antMatchers("/").hasRole(Role.GUEST.name()) //특정("/")url은 권한(GUEST)을 가진 사람만
                .anyRequest().authenticated() //나머지 url들은 모두 인증된 사용자만!
                .and()
                .oauth2Login() //oauth2 login start
                .userInfoEndpoint() //login success-> 사용자 정보를 가져올 때의 설정
                .userService(customOAuth2UserService)
                .and()
                .loginPage("/login"); //login randing

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/error/**", "/dist/**", "/plugins/**", "/resources/**", "/static/**", "/css/**", "/js/**");
    }
}
