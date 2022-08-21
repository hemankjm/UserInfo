/**
 * 하나하나 파고들면, 먼저 request로부터 받은 비밀번호를 암호화하기 위해 PasswordEncoder 빈을 생성했다.
이후 실질적인 로직인 filterChain에 있어서 참조한 블로그 에서는 WebSecurityConfigurerAdapter을 상속받아 사용했다.
그러나 Spring Security는 2022년, 해당 기능을 deprecated 했다.
대신 HttpSecurity를 Configuring해서 사용하라는 대안방식을 제시했으며, 본인 또한 그 방법을 사용했다.https만을 사용하기위해 httpBasic을 disable했으며,
우리는 리액트에서 token을 localstorage에 저장할 것이기 때문에 csrf 방지또한 disable했다.
또한 우리는 REST API를 통해 세션 없이 토큰을 주고받으며 데이터를 주고받기 때문에 세션설정또한 STATELESS로 설정했다.
이후 예외를 핸들링하는 것에서는 이전에 작성했던 JwtAuthenticationEntryPoint와 JwtAccessDeniedHandler를 넣었다.
모든 Requests에 있어서 /auth/**를 제외한 모든 uri의 request는 토큰이 필요하다. /auth/**는 로그인 페이지를 뜻한다.
마지막으로 전에 설정한 JwtSecurityConfig클래스를 통해 tokenProvider를 적용시킨다.
 */

package petfriends.config;




import petfriends.userInfo.jwt.JwtAccessDeniedHandler;
import petfriends.userInfo.jwt.JwtAuthenticationEntryPoint;
import petfriends.userInfo.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// @RequiredArgsConstructor
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  // h2 database 테스트가 원활하도록 관련 API 들은 전부 무시   ????
//   @Override
//   public void configure(WebSecurity web) {
//       web.ignoring()
//           .antMatchers("/h2-console/**", "/favicon.ico");
//   }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
          // CSRF 설정 Disable
      http.csrf().disable()

          // exception handling 할 때 우리가 만든 클래스를 추가
          .exceptionHandling()
          .authenticationEntryPoint(jwtAuthenticationEntryPoint)
          .accessDeniedHandler(jwtAccessDeniedHandler)

          // h2-console 을 위한 설정을 추가  ????
          .and()
          .headers()
          .frameOptions()
          .sameOrigin()

          // 시큐리티는 기본적으로 세션을 사용
          // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

          // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
          .and()
          .authorizeRequests()
          .antMatchers("/auth/**").permitAll()
          .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

          // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
          .and()
          .apply(new JwtSecurityConfig(tokenProvider));
  }
}