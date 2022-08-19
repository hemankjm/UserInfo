/**
 * 일단 제일 윗단의 AUTHORITIES_KEY와 BEARER_TYPE은 토큰을 생성하고 검증할 때 쓰이는 string값이다.
ACCESS_TOKEN_EXPIRE_TIME는 토큰의 만료 시간이다.
key는 JWT 를 만들 때 사용하는 암호화 키값을 사용하기 위해 security에서 불러왔다.

생성자
@Value 어노테이션으로 yml에 있는 secret key를 가져온 다음 이것을 decode한다
이후 의존성이 주입된 key의 값으로 정한다.

generateTokenDto
토큰을 만드는 메소드다.
Authentication 인터페이스를 확장한 매개변수를 받아서 그 값을 string으로 변환한다.

이후 현재시각과 만료시각을 만든 후 Jwts의 builder를 이용하여 Token을 생성한 다음

TokenDto에 생성한 token의 정보를 넣는다.

getAuthentication
토큰을 받았을 때 토큰의 인증을 꺼내는 메소드다.
아래 서술할 parseClaims 메소드로 string 형태의 토큰을 claims형태로 생성한다.

다음 auth가 없으면 exception을 반환한다.

GrantedAuthority을 상속받은 타입만이 사용 가능한 Collection을 반환한다.

그리고 stream을 통한 함수형 프로그래밍으로 claims형태의 토큰을 알맞게 정렬한 이후 SimpleGrantedAuthority형태의 새 List를 생성한다. 여기에는 인가가 들어있다.

SimpleGrantedAuthority은 GrantedAuthority을 상속받았기 때문에 이 지점이 가능하다.

SimpleGrantedAuthority.java
public final class SimpleGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    ...
  }
이후 Spring Security에서 유저의 정보를 담는 인터페이스인 UserDetails에 token에서 발췌한 정보와, 아까 생성한 인가를 넣고,

이를 다시 UsernamePasswordAuthenticationToken안에 인가와 같이 넣고 반환한다.

여기서 UsernamePasswordAuthenticationToken인스턴스는 UserDetails를 생성해서 후에 SecurityContext에 사용하기 위해 만든 절차라고 이해하면 된다.

왜냐하면 SecurityContext는 Authentication객체를 저장하기 때문이다.

validateToken
토큰을 검증하기 위한 메소드다.

parseClaims
토큰을 claims형태로 만드는 메소드다.

이를 통해 위에서 권한 정보가 있는지 없는지 체크가 가능하다.
 */

package petfriends.userInfo.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import petfriends.userInfo.dto.TokenDto;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;


    // 주의점: 여기서 @Value는 `springframework.beans.factory.annotation.Value`소속이다! lombok의 @Value와 착각하지 말것!
    //     * @param secretKey
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // 토큰 생성
    public TokenDto generateTokenDto(Authentication authentication) {

        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}