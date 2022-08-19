/**
 * SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> 인터페이스를 구현하는 구현체다.

직접 만든 TokenProvider와 JwtFilter를 SecurityConfig에 적용할 때 사용한다.

메인 메소드인 configure은TokenProvider를 주입받아서 JwtFilter를 통해 SecurityConfig 안에 필터를 등록하게 되고, 스프링 시큐리티 전반적인 필터에 적용된다.
 */
package petfriends.config;


import lombok.RequiredArgsConstructor;
import petfriends.userInfo.jwt.JwtFilter;
import petfriends.userInfo.jwt.TokenProvider;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 직접 만든 TokenProvider 와 JwtFilter 를 SecurityConfig 에 적용할 때 사용
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    // TokenProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
